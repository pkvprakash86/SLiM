#!/usr/bin/perl

use List::Util qw[min max shuffle];
use LWP::UserAgent;
use HTTP::Request::Common;
use JSON;
use Data::Dumper;

my $gateway = 'http://api.zemanta.com/services/rest/0.0/'; 
my $SRC_DIR = "/data/Project/scripts/Sample";

my @files = ();

sub getTextFiles{
	my $SRCDIR = shift;
	opendir(SRC, $SRCDIR) or die "Can't open $SRCDIR: $!";
	while( defined (my $file = readdir SRC) ) {
		if($file =~ m/.*\.txt$/){
			push(@files, $file);
		}
	}
	closedir(SRC);
}

# simulate change in semantics
sub simulate{
	getTextFiles($SRC_DIR);
	$index = 0;
	@shuffled = shuffle(@files);
	foreach my $i (@shuffled){
		print "File $i\t maps to $files[$index]\n";
		
		print "Processing $i ...\n";
    	open (INFILE, "$SRC_DIR/$i") or die "Cannot open file $SRC_DIR/$i : $!\n";
       	
		my $text = <INFILE>;
		my $args = {	
			method => 'zemanta.suggest',
			api_key => 'meiqcm8gkg8tzaippofc5dxv',
			text => "$text",
			format => 'json',
			return_categories => 'dmoz'	
		};
		
		my $ua = LWP::UserAgent->new;
		my $response = $ua->request(POST $gateway, $args);
		my $result = from_json($response->content);
				
		my $target = $files[$index];
    	$target =~ s/\.txt//;
		print "Writing to $SRC_DIR/$target"."_2.tag\n";
    	open (OUTFILE, ">$SRC_DIR/$target"."_2.tag") or die "Cannot open file $SRC_DIR/$target"."_2.tag : $!\n";
    	
    	print OUTFILE "Categories	:\n";
		foreach $i (@{$result->{'categories'}}){
			print OUTFILE "$i->{'name'}\n";
		}
		print OUTFILE "Keywords	:\n";
		foreach $i (@{$result->{'keywords'}}){
			print OUTFILE "$i->{'name'}\n";
		}
		
		close(INFILE);
		close(OUTFILE);		
    	$index++;
	} 
}


sub getCategories{
	my $file = shift;
	open (INFILE, "$file") or die "Cannot open file $SRC_DIR/$file : $!\n";
	$/ = "Keywords	:\n";
	my $chunk = <INFILE>;
	my @cat = split(/\n/, $chunk);
	close(INFILE);
	splice(@cat, 0,1);
	splice(@cat, -1,1);
	return @cat;
}

#detecting semantic changes
my @detected = ();
sub detect {
	@detected = ();
	my @tag_files = ();	
	opendir(SRC, $SRC_DIR) or die "Can't open $SRC_DIR: $!";
	while( defined (my $file = readdir SRC) ) {
		if($file =~ m/.*\.txt$/){
			push(@tag_files, $file);
		}
	}
	closedir(SRC);
	
	#print "Starting detection..\n";
	foreach  my $file (@tag_files){
		my $tag1_file = $file;
		$tag1_file =~ s/\.txt/_1\.tag/;
		my $tag2_file = $file;
		$tag2_file =~ s/\.txt/_2\.tag/;
		
		#print $tag1_file."\n";
		#print $tag2_file."\n";
				
		my @tags1 	= getCategories("$SRC_DIR/$tag1_file");
		my @tags2 	= getCategories("$SRC_DIR/$tag2_file");
		my $count = 0;
		
		foreach $tag1 (@tags1){
			foreach $tag2 (@tags2){
				my $measure = sim($tag1, $tag2);
				if ($measure > 0.60){
					$count++;
				}
			}
		}
		#print "comparing .. $tag1_file and $tag2_file...";
		#print "$count\n";
		if($count < 2 ){
			push(@detected, {'name'=>$file});
		}
	}
	
	#foreach $file (@detected){
	#	print $file. "\n";
	#}
}

sub publish{
	my $ua = LWP::UserAgent->new;
	my $url = 'http://localhost:8084/SLiM/service/publish';
	$/ = "\n";
	open TGT, "tgt_url.in" or die $!;
	my @tgt = <TGT>;
	close TGT;
	
	foreach $file (@detected){
		my $t = $tgt[rand @tgt];
		chomp($t);
		my $d = $file->{'name'};
		my $req = (POST $url, ['source' => $t, 'data' => $d]);
		my $response = $ua->request($req);
		if ($response->is_success) {
		 	print "INFO\tnotification from \t$t with $d\t".$response->status_line."\n";
	 	} else {
			print "ERROR\tnotification from \t$t with $d\t".$response->status_line."\n";
			#die $response->status_line;
	 	}
	}
	
}



sub sim {
	my @cat1 = split(/\//, shift);
	my @cat2 = split(/\//, shift);
	shift @cat1;
	shift @cat2;
	my $l1 = @cat1;
	my $l2 = @cat2;
	
	my $measure = 0.0;
	my $mn = min($l1,$l2);
	my $mx = max($l1,$l2);
	my $start = 1;
	my $den1 = ($start << $l1);
	my $den2 = ($start << $l2);
	
	my $index=0;

	for ($index = 0; $index < $mn; $index++){
		if($cat1[$index] eq $cat2[$index]){
			$measure += (1/$den1);
			$den1 = $den1 >> 1;
			$measure += (1/$den2);
			$den2 = $den2 >> 1;
		}else{
			last;
		}
	}
	
	my $total = $measure;
	
	while($index < $mn){
		$total += (1/$den1);
		$den1 = $den1 >> 1;
		$total += (1/$den2);
		$den2 = $den2 >> 1;
		$index++;
	}
		
	my $den = 0;
	if($l1 eq $mx){
		$den = $den1;
	} else {
		$den = $den2;
	}
	
	while($index < $mx){
		$total += (1/$den);
		$den = $den >> 1;
		$index++;
	}
	#print "Total : $total\n";
	#print "Measure : $measure\n";
	#print "Index : $index\n";
	$sim_val = ($measure/$total);
	#print "Measure : ".($measure/$total)."\n";
	
	return $sim_val;
}

#$c1 = "Top/Science/Physics/Optics";
#$c2 = "Top/Science/Physics/Optics/Laser";
#print "Value = ".sim($c1, $c2)."\n";
#getCategories("$SRC_DIR/33_1.tag");



#simulate();
detect();
publish();


use LWP::UserAgent;
use HTTP::Request::Common;
use JSON;
use Data::Dumper;

my $gateway = 'http://api.zemanta.com/services/rest/0.0/'; 

my $SRC_DIR = "/data/Project/scripts/Sample";

opendir(SRC, $SRC_DIR) or die "Can't open $SRC_DIR: $!";
while( defined (my $file = readdir SRC) ) {
	if($file =~ m/.*\.txt$/){
		print "Processing $file ...\n";
    	open (INFILE, "$SRC_DIR/$file") or die "Cannot open file $SRC_DIR/$file : $!\n";
       	
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

		
				
		my $target = $file;
    	$target =~ s/\.txt//;
		print "Processing $file to $SRC_DIR/$target.1.tag\n";
    	open (OUTFILE, ">$SRC_DIR/$target"."_1".".tag") or die "Cannot open file $SRC_DIR/$target.1.tag : $!\n";
    	
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
    }
}
closedir(SRC);





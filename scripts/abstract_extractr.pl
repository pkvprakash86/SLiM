#!/usr/bin/perl


my $TARGET_DIR = "/data/Demo/abstract";
my $i=1;

my $SRC_DIR = "/data/Demo/abstract/Part1/awards_1990/awd_1990_01";
opendir(SRC, $SRC_DIR) or die "Can't open $SRC_DIR: $!";
while( defined (my $file = readdir SRC) ) {
	
    if($file =~ m/.*\.txt$/){
    	open (INFILE, "$SRC_DIR/$file") or die "Cannot open file $SRC_DIR/$file : $!\n";
    	open (OUTFILE, ">$TARGET_DIR/$i.txt") or die "Cannot open file $TARGET_DIR/$i.txt : $!\n";
		$/ = "Abstract    :\n";
		<INFILE>;
		my $chunk = <INFILE>;
		$chunk =~ s/\s+|\n/ /g; 
		$chunk =~ s/^\s+//;
		$chunk =~ s/\s+$//;
		print OUTFILE $chunk ."\n";
		$i = $i+1;
		close(INFILE);
		close(OUTFILE);		
    }
}
closedir(SRC);


#!/usr/bin/perl


use warnings;
use strict;
use LWP::UserAgent;
use HTTP::Request::Common qw(POST);

open SRC, "src_url.in" or die $!;
open TGT, "tgt_url.in" or die $!;
open WS, "slim_notify_service.txt" or die $!;

my @src = <SRC>;
my @tgt = <TGT>;
my @ws = <WS>;

close SRC;
close TGT;
close WS;

my $ua = LWP::UserAgent->new;
my $url = 'http://localhost:8084/SLiM/service/subscribe';

my $i = 0;

while($i < 5000){

	my $s = $src[rand @src];
	my $t = $tgt[rand @tgt];
	my $w = $ws[rand @ws];
	chomp($s);
	chomp($t);
	chomp($w);
	
	my $req = (POST $url, ['source' => $s, 'target' => $t, 'ws' => $w]);
	my $response = $ua->request($req);

	if ($response->is_success) {
     	print "INFO\t$i\t$s\t$t\t$w\t".$response->status_line."\n";
     	$i=$i+1;
 	} else {
 		print "ERROR\t$i\t$s\t$t\t$w\t".$response->status_line."\n";
    	#die $response->status_line;
 	}
 	
}

print "Subscription DONE\n";

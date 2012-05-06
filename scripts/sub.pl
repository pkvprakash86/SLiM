#!/usr/bin/perl


use warnings;
use WWW::Curl::Form;
use WWW::Curl::Easy;

open SRC, "src_url.in" or die $!;
open TGT, "tgt_url.in" or die $!;
open WS, "slim_notify_service.txt" or die $!;

my @src = <SRC>;
my @tgt = <TGT>;
my @ws = <WS>;

close SRC;
close TGT;
close WS;

my $i = 0;

my $curl = WWW::Curl::Easy->new;
$curl->setopt(CURLOPT_HEADER,1);
$curl->setopt(CURLOPT_URL, 'http://localhost:8084/SLiM/service/subscribe');
$curl->setopt(CURLOPT_CONNECTTIMEOUT, 5);
$curl->setopt(CURLOPT_TIMEOUT, 120);
#my $response_body;
#$curl->setopt(CURLOPT_WRITEDATA, \$response_body);
#$curl->setopt(CURLOPT_WRITEDATA, 0);

while($i < 10000){

	my $s = $src[rand @src];
	my $t = $tgt[rand @tgt];
	my $w = $ws[rand @ws];
	
	my $curlf = WWW::Curl::Form->new;
	$curlf->formadd("source", $s);
	$curlf->formadd("target", $t);
	$curlf->formadd("ws", $w);
    $curl->setopt(CURLOPT_HTTPPOST, $curlf);
	my $retcode = $curl->perform;
	
	if ($retcode == 0) {
            my $response_code = $curl->getinfo(CURLINFO_HTTP_CODE);
            print "INFO\t$i\t$s\t$t\t$w\t$response_code\n";
            if ($response_code >= 200 and $response_code < 300){
                $i = $i+1;	
            }
    } else {
            # Error code, type of error, error message
            print "ERROR\t$i\t$s\t$t\t$w\t$response_code\n";
            print("An error happened: $retcode ".$curl->strerror($retcode)." ".$curl->errbuf."\n");
    }
}

$curl->cleanup();

print "Subscription DONE";

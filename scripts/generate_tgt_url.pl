#!/usr/bin/perl

open IN, ">tgt_url.in" or die $!;

my $str = "http://127.0.0.1/target/";
$i = 0;

while ( $i < 100){
	print IN $str.$i.".html\n";
	$i = $i+1;	
}



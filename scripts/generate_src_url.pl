#!/usr/bin/perl

open IN, ">src_url.in" or die $!;

my $str = "http://127.0.0.1/source/";
$i = 0;

while ( $i < 100){
	print IN $str.$i.".html\n";
	$i = $i+1;	
}



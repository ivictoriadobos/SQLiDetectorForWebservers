#! /bin/sh
echo Starting tshark
tshark  -i vboxnet0 -Y http.request  -Tfields -E aggregator=' ' -e frame.time -e ip.src -e _ws.expert.message -e http.request.line -e http.file_data
echo Tshark stopped
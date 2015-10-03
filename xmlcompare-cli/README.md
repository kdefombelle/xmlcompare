XmlCompare
======================

----------------------
Introduction
----------------------
XmlCompare is a command line tool that allows you to compare 2 XML and dump the results in an Excel document.<br/>
It is based on [XMLUnit](http://www.xmlunit.org/) for the comparison
As it relies on Excel using [Apcahe POI](https://poi.apache.org/) it is a windows only script.

It produces an Excel report as below:
![alt text](https://raw.githubusercontent.com/kdefombelle/xmlcompare/master/doc/excelReport.png "Lorem Ipsum XML comparison")

The typical usage are illustrated below:

On Windows
```sh
bin\xmlcompare.bat -c sample\loremIpsum1.xml -t sample\loremIpsum1.xml
```
On MacOS
```sh
bin/xmlcompare.sh -c sample/loremIpsum1.xml -t sample/loremIpsum2.xml
```

Ignoring attributes comparison / naming to for your Excel report
```sh
bin/xmlcompare.sh -c sample/loremIpsum1.xml -t sample/loremIpsum2.xml -i -r myReport.xlsx
```

----------------------
Command Line Arguments
----------------------

|Option                 |Description|
|------                 |-----------|
|-c, --control <File>   |control file|
|-h, --help             |print this help message|
|-r, --report           |Excel report file name (default:report-${timestamp}.xlsx)|
|-t, --test <File>      |file to be compared|

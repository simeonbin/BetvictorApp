# Betvictor Random Text Generation Application

The BETVICTOR test project for generating Random Text Paragraphs 
was implemented using Jetbrains IntelliJ, Java 8 compiler and debugger, JEE, Glassfish 4.2.1, JAX-RS 2.0, Jersey 2,
Not Maven this time because of the documentation directions of Jebrains on how to implement REST APIs from scratch.

The following GET REST API Requests were used and executed through the Run/Edit Configuration Window and Dialog Box.

http://localhost:8080/Jersey_3_war_exploded/betvictor/text?p_start=1&p_end=10&w_count_min=1&w_count_max=25

http://localhost:8080/Jersey_3_war_exploded/betvictor/history


Two Files were used, "dummytext.json", "dummytext_calculations.txt"
one to Save the Response from the HTTPURLConnection Call and another to Save the Calculations
(Computations) requested by the Exercise, so that they can be replayed and printed later on on a 
HTTP GET /betvictor/history endpoint. The Calculations Saved were the following: 

Local Date Time
TimeStamp in seconds since EPOCH Time(Midnight, January 1 1970)
Most Frequent Word Found {in All Paragraphs of the Response}
Average Paragraph Size
Average Paragraph Processing Time (in nanos, 10^-9 seconds)
Total Processing Time (in millis, 10^-3 seconds)

The Number of Occurrences of the Most Frequent Word is also saved in the File.


"currentDirectory" where files were created and appended on in my case was the following:
C:\Users\Simeon.000\glassfish-4.1.2-web\glassfish4\glassfish\domains\domain1\config

that is, the glassfish_4.1.2 directory since this project was built as Java Enterprise 'WAR' Application 
running on Glassfish.



The project was implemented from scratch based on the following documentation of the Jetbrains
IntelliJ product, along with surrounding relevant documentation.

https://www.jetbrains.com/help/idea/creating-and-running-your-first-restful-web-service.html



 Sample HttpURLConnection treatment with java code

 br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

               System.out.println("Output from Server .... \n");
                while ((output = br.readLine()) != null) {
                    response.append(output);
                    System.out.println(output);
                }
                System.out.println(response.toString());



Sample code to Create or Append	the dummytext.json File for the Responses from the following Request REST API

http://localhost:8080/Jersey_3_war_exploded/betvictor/text?p_start=1&p_end=10&w_count_min=1&w_count_max=25

 try {
        Files.write ( Paths.get(dirName, "dummytext.json"), response.toString().getBytes(), StandardOpenOption.APPEND );
               } catch (IOException e)
               //exception handling goes here
               { e.printStackTrace(); }				
				
				
				
Sample Content of the dummytext.json File with 3, 4, 5 Paragraph "Request/Responses":
dummytext.json

{"type":"gibberish","amount":3,"number":"25","number_max":"25","format":"p","time":"17:43:38","text_out":

"<p>This a hello oh some the far impetuously as urchin much immaturely so far some much made because because onto 
under far that hummingbird out.<\/p>\r

<p>Cassowary teasingly as jeepers guffawed excepting much poured when far much far instead regarding barked 
ostrich ridiculously apart toucan dear this goose jeepers gave less.<\/p>\r

<p>That hatchet tarantula redoubtably whistled and roadrunner and after thanks far fumbling much much weasel far 
jeepers through far far ladybug as wrung bright disagreed.<\/p>\r"}

{"type":"gibberish","amount":4,"number":"25","number_max":"25","format":"p","time":"17:47:11","text_out":

"<p>Alas taped grateful much hello concise crud following that before that ouch drew inventoried that blanched 
or and some therefore dog and wise sought set.<\/p>\r

<p>Successful groggy indelicately dear up this ostrich one far flamboyant aboard enchanting weasel overheard opossum 
darn mislaid much unbearable human and awkwardly rang but one.<\/p>\r

<p>Including aardvark hummingbird alas from and and threw rhinoceros and opossum satanic less much beside one less 
far and set the near hastily festively much.<\/p>\r

<p>Shuffled crud wow less darn opposite waked so within ape so honorable up toucan overran quail ironic and radiantly 
when alas conic one liberal this.<\/p>\r"}

{"type":"gibberish","amount":5,"number":"25","number_max":"25","format":"p","time":"17:48:10","text_out":

"<p>When copied upheld intricately bald and alas alas fled gave capybara quetzal in jeepers more jeez beyond 
epidemically angelfish opposite outside overshot as interwove much.<\/p>\r

<p>But this less bird rabbit spilled impertinently wishfully ape more scallop supply gosh far one far hit grouped 
furtive some a wow lorikeet darn other.<\/p>\r

<p>Barring lamely invaluably ocelot angelfish ouch constitutionally this unerringly much thoroughly one considering 
worm close during waved cassowary thus the and underneath faultily more scorpion.<\/p>\r

<p>Far more anathematically extensively below tactfully through during since diplomatic cheered a dragonfly dealt 
hazardous some unwilling labrador much hippopotamus and undid incredibly the sheep.<\/p>\r

<p>Jeez and astride hey shamefully vulnerable preparatory confident and far a hence comprehensive laconically 
pleasantly dolphin aboard for foolhardily this abortively far easily repeated mumbled.<\/p>\r"}
				
In general, the above mentioned REST API breaks down to the following REST calls:
				
HTTP GET: http://www.randomtext.me/api/giberish/p-1/1-25
HTTP GET: http://www.randomtext.me/api/giberish/p-2/1-25
HTTP GET: http://www.randomtext.me/api/giberish/p-3/1-25
....
HTTP GET: http://www.randomtext.me/api/giberish/p-10/1-25


After the Response is received, I sit on a Loop from p[aragraph]_start to p[aragraph]_end
and I treat, scrape and "normalize" the Paragraphs, using jsoup.
I remove the '\r' (line feed), '.'(period) and <p>, </p> (paragraph) HTML tags.

So the Paragraphs sit nice and clean on a String.

then I Apply Algorithms shown in code to implement above mentioned computations.
				
Sample code for Starting and Stoping Stopwatch, and finding Duration of processing time for individual paragraphs 
in nanos, and Total per REST API Call in millis.

//Start the Stopwatch of a paragraph processing time
Instant startProcessingPara = Instant.now();
				
// Start the Stopwatch of a paragraph processing time
Instant finishProcessingPara = Instant.now();

// Duration of this paragraph processing time in millis.
timeElapsedParagraph = Duration.between(startProcessingPara, finishProcessingPara).toNanos();
				
				

Recaping again, the following computations are being implemented, debugged and tested in Java Code.

wordMostFreq {most frequent word in whole Response of above mentioned GET REST API Request}

averagePSize, averagePSize_rounded  {Average Paragraph Size, and rounded in 2 decimals}

averagePElapsedTime (in nanos), averagePElapsedTime_rounded (in nanos with 2 decimal points),
{Average Paragraph Processing Time (in nanos), rounded with 2 decimal points}

timeElapsedTotalProcessingText (in millis) {TOTAL Processing Time per Request/Response}

numOfOccur {Number of Occurrences of the Most Frequent Word}

Sample Java code for finding the Local Date and Time, and TimeStamp in seconds from EPOCH Time
LocalDateTime localDateTime = LocalDateTime.now();
        Instant instant = Instant.now();
        long timeStampSeconds = instant.getEpochSecond();

localDateTime.toString(), timeStampSeconds (in seconds since 1/1/1970T00:00:00Z, Midnight 1 January 1970)


Sample Content of "dummytext_calculations.txt" File follows
localDateTime,timeStamp, wordMostFreq, numOfOccur, averagePSize, averagePElapsedTime(nanos), timeElapsedTotalProcessingText(millis)

2018-12-30T23:51:24.637,1546206684,far,26,12.09,381818.18,4
2018-12-31T00:34:22.526,1546209262,far,28,14.33,581818.18,4
2018-12-31T01:21:48.445,1546212108,far,28,12.25,5709090.91,4
2018-12-31T01:41:31.185,1546213291,far,38,13.22,654545.45,5
2018-12-31T01:41:31.230,1546213291,and,30,13.91,690909.09,9
2018-12-31T01:45:59.663,1546213559,far,33,11.75,709090.91,5
2018-12-31T01:54:44.248,1546214084,far,27,11.98,654545.45,4
2018-12-31T02:02:00.417,1546214520,much,33,11.98,381818.18,5
2018-12-31T02:08:48.917,1546214928,much,31,11.55,1200000.0,4
2018-12-31T02:08:48.921,1546214928,far,28,11.42,1236363.64,4
2018-12-31T02:24:52.962,1546215892,much,20,11.38,1200000.0,8
2018-12-31T02:32:30.553,1546216350,much,28,12.56,800000.0,7
2018-12-31T02:41:45.281,1546216905,and,29,15.33,581818.18,8
2018-12-31T02:41:45.280,1546216905,much,29,13.64,690909.09,10
2018-12-31T02:50:58.789,1546217458,much,12,25.0,333333.33,13
2018-12-31T02:50:59.328,1546217459,far,31,14.33,436363.64,15
2018-12-31T03:26:07.613,1546219567,far,39,13.6,781818.18,5
2018-12-31T04:33:56.752,1546223636,much,28,12.11,254545.45,6
2018-12-31T04:33:56.752,1546223636,and,25,11.25,1345454.55,8
2018-12-31T04:40:29.939,1546224029,far,26,13.65,381818.18,10
2018-12-31T04:48:35.959,1546224515,and,28,12.27,309090.91,7
2018-12-31T05:02:45.743,1546225365,and,27,13.56,563636.36,6
2018-12-31T05:04:37.771,1546225477,and,24,13.53,1090909.09,6
2018-12-31T05:07:57.556,1546225677,much,27,12.02,454545.45,6
2018-12-31T05:10:22.741,1546225822,much,37,14.16,545454.55,6
2018-12-31T05:13:01.170,1546225981,far,30,13.0,400000.0,7
2018-12-31T05:44:30.975,1546227870,far,40,14.04,345454.55,5
2018-12-31T15:56:39.365,1546264599,far,28,12.75,400000.0,7
2018-12-31T20:47:53.863,1546282073,and,33,13.13,309090.91,16
2018-12-31T20:47:53.871,1546282073,and,29,12.56,490909.09,10
2019-01-02T00:05:11.058,1546380311,and,35,14.15,436363.64,5
2019-01-02T11:02:38.194,1546419758,far,24,11.13,363636.36,6
2019-01-02T11:07:45.709,1546420065,and,23,11.64,436363.64,6
2019-01-02T14:40:27.832,1546432827,and,6,13.18,345454.55,7
2019-01-02T14:49:43.560,1546433383,far,7,12.58,436363.64,6
2019-01-02T14:53:30.526,1546433610,and,7,13.42,381818.18,41
2019-01-02T15:00:01.319,1546434001,far,8,11.98,418181.82,13
2019-01-02T15:09:37.821,1546434577,and,7,13.67,381818.18,5
2019-01-02T15:27:00.094,1546435620,much,6,14.51,763636.36,472
2019-01-02T15:37:36.314,1546436256,far,6,14.73,1.1221818182E8,4
2019-01-02T15:45:12.805,1546436712,that,3,11.45,1672727.27,12
2019-01-02T15:48:12.282,1546436892,and,7,12.25,2527272.73,104
2019-01-02T15:48:11.083,1546436891,far,8,13.09,3.541818182E7,4
2019-01-02T15:48:21.448,1546436901,and,7,12.67,1.34E7,604
2019-01-02T18:47:23.012,1546447643,a,4,12.44,436363.64,9
2019-01-02T19:49:21.723,1546451361,and,6,13.13,381818.18,6
2019-01-02T20:27:26.262,1546453646,less,6,13.07,309090.91,6
2019-01-02T20:47:31.655,1546454851,much,5,12.98,381818.18,7
2019-01-02T20:48:45.113,1546454925,much,6,12.75,109090.91,4
2019-01-02T20:49:04.355,1546454944,that,5,13.11,272727.27,6


In case of endpoint /betvictor/history I read the contents of "dummytext_calculations.txt" File from Current Directory:
C:\Users\Simeon.000\glassfish-4.1.2-web\glassfish4\glassfish\domains\domain1\config

to a List of Strings, I use  Collections.reverse(listHistoryOfComputations); to Reverse on 
Timestamp (seconds from EPOCH Time) which totally reverses the order, goes from more recent to older Calculations.
Then I send them to LOGGER.

If I could invest some more time, I could use a Table from a Relational Database instead of a CSV type Text File.
And I would research the Unit Testing environment of IntelliJ IDEA on Web Services so that I can improve Unit Testing
using maybe JUNit, AssertJ, TestNG, and Mockito to Unit Test the project more thoroughly.
In this particular case, Algorithms were relatively trivial and I tested them through the Run/Edit Configuration 
URL TextBox and tracing code through the IntelliJ Java debugger.

At your disposal for any questions,
Simeon Biniatidis





























				
to Read and Parse EU VAT Rates from "http://jsonvat.com"
Sort them, Print "Countries with the smallest / largest standard VAT:


main(String[] args) method exists in BarclaysVAT.java under /src/main/java directory.

Implemented using Jetbrains IntelliJ with Java 8, and Maven.

The JsonArray Element below is a typical Array-Element value in "rates" Key-Entry in the JSON File.


{"name":"Luxembourg","code":"LU","country_code":"LU",
"periods":[

{"effective_from" : "2016-01-01",
"rates": {"super_reduced":3.0,"reduced1":8.0,"standard":17.0,"parking":13.0}
},

{"effective_from":"2015-01-01",
"rates": {"super_reduced":3.0,"reduced1":8.0,"reduced2":14.0,"standard":17.0,"parking":12.0}
},

{"effective_from" : "0000-01-01",
"rates" : {"super_reduced":3.0,"reduced1":6.0,"reduced2":12.0,"standard":15.0,"parking":12.0} }
] 
},

A typical run of the Project-Module {BarclaysVATApp} gives the following console output

Countries with the smallest standard VAT:

	Luxembourg     LU    17,0
	
	Malta          MT    18,0
	
	Cyprus         CY    19,0
	
Countries with the largest standard VAT:

	Hungary        HU    27,0
	
	Denmark        DK    25,0
	
	Croatia        HR    25,0
	
	
Process finished with exit code 0

Furthermore there is 4 Tests, using JUnit and AssertJ, for Unit Testing the methods.

There are 28 Country entries all-together in the JSON File, as shown further down in this ReadMe.md,
preceded by a 1-liner header.

The software programming Task was: For every EU Country, which is represented by a JsonArray Element of the "rates" key,
parse the "rates" key contained in the value of the "periods" key.
Then find the most recent "effective-from" Date, read the "standard" VAT-rate value of it and save it ia data-structure.
Then Sort the data-structure in ascending and descending order, and print the THREE (3) smallest and largest Country-rates.


// JSON File for EU-Country VAT rates.

//---------------------------------------------------------------------------------------
{"details":"http://github.com/adamcooke/vat-rates","version":null,"rates":[

{"name":"Spain","code":"ES","country_code":"ES","periods":[{"effective_from":"0000-01-01","rates":{"super_reduced":4.0,"reduced":10.0,"standard":21.0}}]},

{"name":"Bulgaria","code":"BG","country_code":"BG","periods":[{"effective_from":"0000-01-01","rates":{"reduced":9.0,"standard":20.0}}]},

{"name":"Hungary","code":"HU","country_code":"HU","periods":[{"effective_from":"0000-01-01","rates":{"reduced1":5.0,"reduced2":18.0,"standard":27.0}}]},

{"name":"Latvia","code":"LV","country_code":"LV","periods":[{"effective_from":"0000-01-01","rates":{"reduced":12.0,"standard":21.0}}]},

{"name":"Poland","code":"PL","country_code":"PL","periods":[{"effective_from":"0000-01-01","rates":{"reduced1":5.0,"reduced2":8.0,"standard":23.0}}]},

{"name":"United Kingdom","code":"UK","country_code":"GB","periods":[{"effective_from":"2011-01-04","rates":{"standard":20.0,"reduced":5.0}}]},

{"name":"Czech Republic","code":"CZ","country_code":"CZ","periods":[{"effective_from":"0000-01-01","rates":{"reduced":15.0,"standard":21.0}}]},

{"name":"Malta","code":"MT","country_code":"MT","periods":[{"effective_from":"0000-01-01","rates":{"reduced1":5.0,"reduced2":7.0,"standard":18.0}}]},

{"name":"Italy","code":"IT","country_code":"IT","periods":[{"effective_from":"0000-01-01","rates":{"super_reduced":4.0,"reduced":10.0,"standard":22.0}}]},

{"name":"Slovenia","code":"SI","country_code":"SI","periods":[{"effective_from":"0000-01-01","rates":{"reduced":9.5,"standard":22.0}}]},

{"name":"Ireland","code":"IE","country_code":"IE","periods":[{"effective_from":"0000-01-01","rates":{"super_reduced":4.8,"reduced1":9.0,"reduced2":13.5,"standard":23.0,"parking":13.5}}]},

{"name":"Sweden","code":"SE","country_code":"SE","periods":[{"effective_from":"0000-01-01","rates":{"reduced1":6.0,"reduced2":12.0,"standard":25.0}}]},

{"name":"Denmark","code":"DK","country_code":"DK","periods":[{"effective_from":"0000-01-01","rates":{"standard":25.0}}]},

{"name":"Finland","code":"FI","country_code":"FI","periods":[{"effective_from":"0000-01-01","rates":{"reduced1":10.0,"reduced2":14.0,"standard":24.0}}]},

{"name":"Cyprus","code":"CY","country_code":"CY","periods":[{"effective_from":"0000-01-01","rates":{"reduced1":5.0,"reduced2":9.0,"standard":19.0}}]},

{"name":"Luxembourg","code":"LU","country_code":"LU","periods":[{"effective_from":"2016-01-01","rates":{"super_reduced":3.0,"reduced1":8.0,"standard":17.0,"parking":13.0}},{"effective_from":"2015-01-01","rates":{"super_reduced":3.0,"reduced1":8.0,"reduced2":14.0,"standard":17.0,"parking":12.0}},{"effective_from":"0000-01-01","rates":{"super_reduced":3.0,"reduced1":6.0,"reduced2":12.0,"standard":15.0,"parking":12.0}}]},

{"name":"Romania","code":"RO","country_code":"RO","periods":[{"effective_from":"2017-01-01","rates":{"reduced1":5.0,"reduced2":9.0,"standard":19.0}},{"effective_from":"2016-01-01","rates":{"reduced1":5.0,"reduced2":9.0,"standard":20.0}},{"effective_from":"0000-01-01","rates":{"reduced1":5.0,"reduced2":9.0,"standard":24.0}}]},

{"name":"Estonia","code":"EE","country_code":"EE","periods":[{"effective_from":"0000-01-01","rates":{"reduced":9.0,"standard":20.0}}]},

{"name":"Greece","code":"EL","country_code":"GR","periods":[{"effective_from":"2016-06-01","rates":{"reduced1":6.0,"reduced2":13.5,"standard":24.0}},{"effective_from":"2016-01-01","rates":{"reduced1":6.0,"reduced2":13.5,"standard":23.0}},{"effective_from":"0000-01-01","rates":{"reduced1":6.5,"reduced2":13.0,"standard":23.0}}]},

{"name":"Lithuania","code":"LT","country_code":"LT","periods":[{"effective_from":"0000-01-01","rates":{"reduced1":5.0,"reduced2":9.0,"standard":21.0}}]},

{"name":"France","code":"FR","country_code":"FR","periods":[{"effective_from":"2014-01-01","rates":{"super_reduced":2.1,"reduced1":5.5,"reduced2":10.0,"standard":20.0}},{"effective_from":"2012-01-01","rates":{"super_reduced":2.1,"reduced1":5.5,"reduced2":7.0,"standard":19.6}},{"effective_from":"0000-01-01","rates":{"super_reduced":2.1,"reduced1":5.5,"standard":19.6}}]},

{"name":"Croatia","code":"HR","country_code":"HR","periods":[{"effective_from":"0000-01-01","rates":{"reduced1":5.0,"reduced2":13.0,"standard":25.0}}]},

{"name":"Belgium","code":"BE","country_code":"BE","periods":[{"effective_from":"0000-01-01","rates":{"reduced1":6.0,"reduced2":12.0,"standard":21.0,"parking":12.0}}]},

{"name":"Netherlands","code":"NL","country_code":"NL","periods":[{"effective_from":"2012-10-01","rates":{"reduced":6.0,"standard":21.0}},{"effective_from":"0000-01-01","rates":{"reduced":6.0,"standard":19.0}}]},

{"name":"Slovakia","code":"SK","country_code":"SK","periods":[{"effective_from":"0000-01-01","rates":{"reduced":10.0,"standard":20.0}}]},

{"name":"Germany","code":"DE","country_code":"DE","periods":[{"effective_from":"0000-01-01","rates":{"reduced":7.0,"standard":19.0}}]},

{"name":"Portugal","code":"PT","country_code":"PT","periods":[{"effective_from":"0000-01-01","rates":{"reduced1":6.0,"reduced2":13.0,"standard":23.0,"parking":13.0}}]},

{"name":"Austria","code":"AT","country_code":"AT","periods":[{"effective_from":"2016-01-01","rates":{"reduced1":10.0,"reduced2":13.0,"standard":20.0,"parking":13.0}},{"effective_from":"0000-01-01","rates":{"reduced":10.0,"standard":20.0,"parking":12.0}}]}]}



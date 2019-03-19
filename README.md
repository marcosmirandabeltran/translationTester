# translationTester

# UNDER DEVELOPMENT, fully functional POC:

The purpose of this microservice, its expose a number of different API's for the Software UI testing of different projects.

For a given JSON localization file (key: String) or a given segment, and a particular target-language the application should reply with the original segment with local characters of the target language and a expansion factor applied.


Update (19/03/2019)


## Explanation of key concepts:

### expansion:

extra info: possible values
0 = no expansion (except for starting_chars and ending_chars added)
1 = some expansion (double randomly approximately every 1 of 6 characters)
2 = maximum expansion (double randomly approximately every 1 of 3 characters)
note expansion for shorter strings (< 5) pushed to every 1 of 2, if string_expansion not 0
note expansion for string 5 - 7 chars in length pushed to every 1 of 4, if string_expansion not 0


### URLS:

URLS shouldn't be localized/translated, so for the string "Lorem ipsum www.google.ie dolor sit amet" , the service should return something like this in korean with expansion 2 , "LL어어rre갑e갑mm ipssu삯u삯m www.google.ie d어ll어rr ssiitt"

### LANGUAGE CODES:

If one of the segments contain is a language code instead of a regular string , like en-US should be replaced for the particular code for the target language:

example:
{
	"jsonBody": "{\"field1\" : \"en-US\", \"field2\" : \"value2\"}",
	"trgLng":"fr",
	"expansion": 0
}
should return [
    "fr-FR",
    "välüé2"
]

same with ENU (international language code) or english

## How to start a service  

1- Clone the repo
2- From the repo folder, use the command "sbt run"  (NOTE: This requires SBT , scala and a JVM installed in the computer)


# REST API

This will expose a web server on the port 8080 (by default, should be configurable in a later stage of the development), there is 3 types of requests accepted:
```
POST /json:
> 
{
	"jsonBody": "JSON LOCALIZATION FILES WITH KEY: STRINGS",
	"trgLng":"Language code , see file languagemapping.json for more language codes, a valid example would be ko for korean",
	"expansion": (0, 1, or 2) as an int , no quotes, look for expansion documentation in this doc for reference
}
>
Example:

{
	"jsonBody": "{\"field1\" : \"Lorem ipsum dolor sit amet, consectetur adipiscing elit.\", \"field2\" : \"value2\"}",
	"trgLng":"ko",
	"expansion": 2
}

Expected output: (Two strings in korean with expansion factor 2
[
    "LL어어rre갑m ipssu삯u삯mm dd어어ll어어rr ssitt a逸a逸mme갑e갑tt,, c어nse갑e갑ctte갑ttu삯rr a逸dipiscinngg e갑e갑lit.",
    "vva逸a逸llu삯e갑e갑22"
]


POST /json:

{
	"jsonBody": "JSON LOCALIZATION FILES WITH KEY: STRINGS",
	"trgLng":"Language code , see file languagemapping.json for more language codes, a valid example would be ko for korean",
}


POST /segments:
{"segments": ["Hello I am Marcos", "How are you?"],
"trgLng":"de"
}
```


(More info to be added)



Note: Delete the .idea project and target folders on the repo.

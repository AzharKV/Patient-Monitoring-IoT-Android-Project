#include "ThingSpeak.h"
#include <ESP8266WiFi.h>
#include <DHT.h>
#define DHTPIN D2
#define DHTTYPE DHT11
DHT dht(DHTPIN, DHTTYPE);
char ssid[] ="okgoogle";   // your network SSID (name) 
char pass[] ="pewo9080" ;   // your network password
int keyIndex = 0; 
int r;  
WiFiClient  client;
unsigned long ChannelNumber =890485;

const char * writeAPIKey ="FUJ9ID74RQ6TIREW" ;
int beat();
void setup() {

 Serial.begin(9600);  // Initialize serial
pinMode(A0,INPUT);
  WiFi.mode(WIFI_STA); 
  ThingSpeak.begin(client);
  Serial.println("DHTxx test!");
  dht.begin();
  }

void loop() {
  if(WiFi.status() != WL_CONNECTED){
    Serial.print("Attempting to connect to SSID: ");
    Serial.println("evalin rapid");
    while(WiFi.status() != WL_CONNECTED){
      WiFi.begin(ssid, pass);  
      Serial.print(".");
      delay(5000);     
    } 
    Serial.println("\nConnected.");
  }
    if(analogRead(A0)==1024)
  {
        
    r=random(68,74);
    Serial.println(r);
  }
  
   float h = dht.readHumidity(); 
   float t = dht.readTemperature();
  
   ThingSpeak.setField(1, r);
   ThingSpeak.setField(2, h);
   ThingSpeak.setField(3, t);
   int x = ThingSpeak.writeFields(ChannelNumber, writeAPIKey);
  if(x == 200){
    Serial.println("Channel update successful.");
  }
  else{
    Serial.println("Problem updating channel. HTTP error code " + String(x));
  }
    
  
  delay(2000); // Wait 20 seconds to update the channel again
}

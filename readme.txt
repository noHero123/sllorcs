jre7 (or 1.7?) is needed, higher is not possible, because of org.json

for sqlite we use:
https://bitbucket.org/xerial/sqlite-jdbc/downloads
(I used 3.8.10.1 (2015-05-18))


for server, per default, the ports 8082-8084 are used, so forward/open them in your router (change the ports in InterThreadStuff.jar)


to change the ip of the server, change in MainClass.java -> public static void main(.) this line:
InterThreadStuff.getInstance().usedServerIp = "91.32.91.127"; //ip of server




for easy connect (didnt implement an auth-server jet)
1.stepp
install summoner (https://github.com/noHero123/ScrollsModLoader/releases) and execute it.

2.stepp
(win:)
go to C:/Users/xxxxx/AppData/LocalLow/Mojang/Scrolls/
and add a server_ip.txt file and insert the ip + 8082 (or an other port of the lookup server, 8082 is default  value)
example: 
127.0.0.1 8082
(please use not a ":" between ip and port, use a space)
for unix + mac, the path where scrolls is searching the server_ip.txt, is written at the start of output_log.txt file
(line 186 for me)

3. stepp
start the server (java -jar sllorcs.jar)

4. stepp
and use the created summoner-xxxx.bat file to start the client.

- DELETE/rename the server_ip.txt file to connect to the orginal servers again!

for easy connect (didnt implement an auth-server jet)
install summoner, and use the summoner.bat file to start the game.


for connecting multiple clients on same pc, make a copy of summoner-xxxxx.bat, open it in txt-editor and change the username (--username "xxxxx")
and start this new summoner-xxx.bat ,too.

if the username is "test" (--username "test")
both players will start with 100 special (wild) ressources
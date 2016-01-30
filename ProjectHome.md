<b>What is apk-recovery?</b><br>
Apk-recovery software is just a convenient tool which might help Android developers to recover main resources from their apk file (after hardware disaster for example...).<br>
It consists in java glue code between other tools like apktool and dex2jar.<br>
Apk-recovery software is available in command line mode and then very easy to use:<br>
<b>java -jar apkRecovery <path to your apk file></b><br><br>
<b><font color='#ff0000'>VERY IMPORTANT:</font> </b><i><b><font color='#ff0000'>This software is NOT intended for piracy and other non-legal uses.</font></b></i><br>
<i><b><font color='#ff0000'>So, topics like recovering .apk files from phone or stuff like providing any kind of <i>"out of the box solution"</i> to quickly modify .apk file from your favorite IDE will never make sense here!.</font></b></i>
<br><br>
<b>Features:</b><br>
- Decrypt apk resources (to original human readable format as xml) like manifest file, res and assets directories, and provide a smali directory (sometimes useful)... apktool job.<br>
- Reverse classes.dex file to jar file which contains all .class files... dex2jar job.<br>
The only last step to achieve is to decompile jar file (which contains .class files) to java language.<br>
(The powerful and convenient jd-gui decompiler is provided to achieve this step....but you can of course use your favorite decompiler tool!)<br><br>
<b>Requierements</b>:<br>
JRE 1.6 (Java Runtime Environment).<br><br>
<b>Tested configurations:</b><br>
- Windows XP/Vista <i>32 bits</i>.<br>
- Linux Ubuntu 10.4 <i>32 bits</i>.<br><br>
<b>Sources:</b><br>
Use this command to anonymously check out the latest project source code:<br>
# Non-members may check out a read-only working copy anonymously over HTTP.<br>
svn checkout <a href='http://apk-recovery.googlecode.com/svn/trunk/'>http://apk-recovery.googlecode.com/svn/trunk/</a> apk-recovery-read-only<br><br>
<b>Demo:</b><br>
A video is available on youtube <a href='http://www.youtube.com/watch?v=mYY9MAt5z6o'>here</a><br>(Tweaking resolution accuracy to higher level does not make sense here: This video only stands for a quick visual getting started guide... And  "<i>required technical skills</i>" needed to run apk-recovery software are really very basic!)<br><br>
<b>Road Map:</b><br>
- Provide jd-gui for Linux users.<br>
- Provide an Eclipse plugin (ADT plugin contribution).<br>
<br>
<b>Related projects:</b><br>
- <a href='http://code.google.com/p/android-apktool'>apktool</a><br>
- <a href='http://code.google.com/p/dex2jar/'>dex2jar</a><br>
- <a href='http://java.decompiler.free.fr/?q=jdgui#downloads'>jd-gui</a> (for windows users)<br><br>
<b>Changelog:</b><br>
- <i>2011.03.01:</i> -<b>v1.0.1</b> - jd-gui decompiler support is now available for Linux and Windows users both (<i>32 bits</i> OS versions).<br>
- <i>2011.02.27:</i> -<b>v1.0.0</b> - Binaries are available for Linux and Windows users (<i>32 bits</i>)<br>
<br>
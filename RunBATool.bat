SET jcdll=C:\SDI_BATool\dist\jcom.dll
%jcdll%
if not exist %jcdll% copy "C:\SDI_BATool\jcom.dll" %jcdll%

java -jar -Xms1g -Xmx1g "C:\SDI_BATool\dist\SDI_BATool.jar"

pause



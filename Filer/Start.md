# Start af programmer

I dette dokument findes vejledning til hvordan de udviklede programmer køres. Både det grundlæggende og det avancerede program kan køres med intern og ekstern JavaFX. Strenge for begge muligheder er angivet nedenfor.



<b>Bemærk: </b> Strengene skal stå på én linje. Det kan være nødvendigt at indsætte strengen i Notepad eller lign. for at sikre sig strengen står på én linje.



### Start af grundspillet

<b>Intern JavaFX</b>

```bash
"C:\STI\TIL\JAVA15+JAVAFX15\bin\java.exe" -Xss32768k -jar Gruppe28-simpel.jar XX YY ZZ
```

Hvor XX, YY, ZZ angiver henholdsvis bredde, højde og antal miner.

<b>Ekstern JavaFX</b>

```bash
java --module-path "C:\STI\TIL\JAVAFX15\lib" --add-modules javafx.controls,javafx.fxml,javafx.media -Xss32768k -jar Gruppe28-simpel.jar XX YY ZZ
```

Hvor XX, YY, ZZ angiver henholdsvis bredde, højde og antal miner.



### Start af avanceret spil

<b>Intern JavaFX</b>

```bash
"C:\STI\TIL\JAVA15+JAVAFX15\bin\java.exe" -Xss32768k -jar Gruppe28-avanceret.jar
```

<b>Ekstern JavaFX</b>

```bash
java --module-path "C:\STI\TIL\JAVAFX15\lib" --add-modules javafx.controls,javafx.fxml,javafx.media -Xss32768k -jar Gruppe28-avanceret.jar 
```


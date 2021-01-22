# Start af programmer

I dette dokument findes vejledning til hvordan de udviklede programmer køres. Både det grundlæggende og det avancerede program kan køres med intern og ekstern JavaFX. Strenge for begge muligheder er angivet nedenfor.

### Start af grundspillet

<b>Intern JavaFX</b>

```bash
"C:\STI\TIL\JAVA15+JAVAFX15\bin\java.exe" -Xss32768k -jar Main.jar XX YY ZZ
```

Hvor XX, YY, ZZ angiver henholdsvis bredde, højde og antal miner.

<b>Ekstern JavaFX</b>

```bash
java --module-path "C:\STI\TIL\JAVAFX15\lib" --add-modules javafx.controls,javafx.fxml,javafx.media -Xss32768k -jar Main.jar XX YY ZZ
```

Hvor XX, YY, ZZ angiver henholdsvis bredde, højde og antal miner.



### Start af avanceret spil

<b>Intern JavaFX</b>

```bash
"C:\STI\TIL\JAVA15+JAVAFX15\bin\java.exe" -Xss32768k -jar Main.jar
```

<b>Ekstern JavaFX</b>

```bash
java --module-path "C:\STI\TIL\JAVAFX15\lib" --add-modules javafx.controls,javafx.fxml,javafx.media -Xss32768k -jar Main.jar 
```


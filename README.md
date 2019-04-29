# TicTacToe
## Struktur
Kode strukturen er splittet opp etter Modell View Controller (MVC) tankegangen. Det vil si å splitte opp kode ut fra ansvar. Modell filer inneholder ren logikk som ikke er avhengig av å bli koblet til noe grafisk grensesnitt for å kunne eksekvere. View folderen inneholder design filer for hvordan knapper, lister og andre resussjera skal presenteres til bruker. Controller filer er bindeledd mellom View og Modell filer. De oppdaterer View filer ut fra loggia utført  i egen kode eller av en Modell fil. Denne strukturen var felles for begge prosjektene

### View
Alle View filer ligger under res/layout/ folderen. Filene i denne folderen skal ha en til en sammenheng med antall Controller filer som ligger under Controller folderen. Disse filene er filformatet XML som er standard for Android.

### Model 
Model folderen i begge prosjektene inneholder en database folder som inneholder de lokale database filene. Ellers ligger annen unik logikk for prosjektene i denne folderen. Disse filene er ren logikk og totalt uavhengig av grafiske elementer

### Kontroller
Filene I denne folderen er activity og fragment filer. Begge prosjektene benytter seg av en fragment arkitektur, men på to forskjellige måter. Applikasjon 1 har en aktivitet som bruker en fragment manager som bytter ut fragmentene ut fra hvem som skal vises på skjerm, mens applikasjon 2 har en MainActivity som setter opp to forskjellige lister (som er fragmenter) og presterer dem på skjerm til brukeren. 

# Applikasjon 1: Tic Tac Toe
Dette er min implementasjon av spillet Tre på rad (Tic Tac Toe). Appen lar deg spille spiller mot spiller eller spiller mot datasmasjonen. Det er tre implementasjoner av datamaskinen som er mulig å spille mot. Da spillet er ferdig blir det beregnet en poengsum avhengig av hem eller hva man spiller mot  så blir en lokal database oppdatert med de nye poengsummene.

 Man kan alltid se hvem som har vært best med å trykke inn på High Score listen. Den er startet etter hvem som har den høyeste poengsummen. Alle tre instansene av data logikk vil også være representert med hver sitt navn. Dette ble supert om i forkant for å få godkjent av foreleser da det står i oppgaven at den skal være representert med TTBot (Ingen av implementasjonene heter dette). 

Appen har Hitchhiker’s Guide to the galaxy som tema.

## Kode filer
### Kontroller 
#### SplashActivity
Hele applikasjonen starter i `AppCompatActivity` filen SplashActivity. Dette er skjermen som viser testen Don’t panic for så å gå til MainActivity filen. Filen benytter seg av en `Runnable` som generer et intent som skaper overgangen til MainActivity. Dette er en meget enkel fil som kun er ment å sette tonen for applikasjonen. 

#### MainActivity
Denne aktiviteten gjør i utgangspunktet to oppgaver. Den håndterer musikk avspilleren og viser til bruker knappen de kan trykke for å oppdatere avspilleren. Dette gjøres av metoden `handleMusicPlayer()`. I `onCreate()` blir en instans av `MediaPlayer` generert. 

Den viktigste oppgaven riktig nok for MainActivity er håndteringen av fragments og utbyttingen som forekommer. Dette gjøres gjennom en rekke kall avhengig av hvilken fragment som skal vises og hvilket innhold de skal ha. Den som blir presentere avhenger av hvor i flyten du er. Tegning over flyt ligger under avsnittet  _Arkitektur og flyt_. 

#### MainMenuFragment
Denne fragmenten presenterer første meny med tre alternativer. Spiller mot spiller, spiller mot datamaskin eller se High score. Deretter sender den data tilbake til MainActivity slik at den vet hva som er neste som skal presenteres.

#### ChoosePlayerFragment
Denne fragmenten brukes i tre forskjellige situasjoner. Den lar deg velge spiller 1 mot datamaskinen, den lar deg velge spiller 1 i PvP modus og den lar de velge spiller 2. Alt innhold blir programmatisk byttet ut. Den passer også på at da du velger spiller to så kan du ikke velge samme spiller som den spiller 1 velge. Dette er for å sikker at du ikke kan spille mot deg selv.

#### SetUpGameAgainstAIFragment
Denne fragmenten lar deg velge AI motstander vanskelighetsnivå. Dette blir sendt som en enum til `GameAI` filen. Mer om denne senere. Etter det vil denne fragmnetne aktivere game `BoardFragment`.

#### BoardFragment
Dette er fragmenten som hpndterer og oppdaterer spille. Den tar imot et parameter da den blir generert av typen `TicTakToe`. Bordfragmenten oppdaterer utfri bruker input og hva som blir returnert tilbake fra `TicTakToe` klasse filen. Da et spill er fullført så vil BoardFragment sende data tilbake til `MainActivity` som vil da oppdatere UI og presentere `GameOverFragment` til bruker.

#### GameOverFragment
Avhengig av hva som forekommer i `TicTakToe` så vil denne GameOver fragmenten presantere forskjellige data tilbake til bruker. Den viktigste oppdaget den gjør er riktig nok å oppdatere den lokale databasen med resultatet fra spille. Den oppdaterer hvilken spiller som vant, hvor mange poeng den spilleren skal nå ha. Hvis spilleren ikke allerede eksisterer i databasen så vil den her bli lagt til. 

#### HighScoreFragment
Presenterer i en recycler view en liste over alle spillere registrert i den lokale databasen. Listen er sortert etter hvem som har mest poeng til den som har minst. Det er ikke nødvendig vis den spilleren som har vunnet mest. Dette er et bevist valg jeg valgte å gjøre da dette gjør det vanskeligere å komme hele veiene til tops i lista. Spesielt hvis du spiller noen runder mot Hard eller Impossible modusene. 


### Model
#### TicTakToe
Dette er en klasse som kan håndtere spill mellom spiller mot spiller eller spiller mot AI. Den har to konstruktører som håndterer dette i start av spillet. 

Denne klassen benytter et en dimensjonalt array for å holde styr på brette og hvilke plasser som er opptatt. Spiller en er representert med tallet -1 og AI med tallet 1. Dette skjedde fordi dette ga mest mening da jeg satte opp MiniMax algoritmen og da jeg implementerte brettet i denne klassen så var det da bare enkelt å bruke akkurat den samme strukturen.  

constructor (player1: String, player2: String) 
constructor (player: String, difficulty: GameMode)
Denne klassen har en rekke public metoder. De fleste av disse er tettere for informasjon om brettet og hvem sin tur det er til å spille, men den har også to viktige metoder for å kunne oppdatere brette avhengig av hvilken kontraktør som er benyttet. 

fun makePlayerMoveIfLegal(position: Int): Boolean
Returner en bool dersom trekke bruker ønsker å gjøre er lovlig. Den oppdaterer også sitt interne spill med dette trekke også. 

fun makeAIMove(): Int 
Dette kallet bruker den indre Game AI instansen til å gjennomføre et AI trekk. Den returnerer en int som representerer hvor på brettet AI-en ønsker å sette brikken sin. 

#### GameAI
Jeg lærte om [MiniMax] algoritmen på denne lenken og bestemte meg da for å implementere den i koden min. Game AI klassen er tilsynelatende enkelt fra utsiden. Den trenger en Enum klasse kalt `GameMode` hvor man velger hvilken vanskelighetsgrad AI-en skal være på. Deretter har den kun tre offentlige metoder `fun getGameMode():GameMode` som kun returnerer Enum modusen som er valgt. Offentlig metode nummer to er `fun getAIName():String` som kun returnere navn på Ai. De forskjellige Ai-ene hr forskjellige navn avhengig av hvilken `GameMode` som er valgt. 

fun makeMove(board: Array<Int>): Int
Dette er den viktigste kallet man kan gjøre med Game AI-en. Den trenger å motta et brett av typen `Array<Int>`. Ut fra hvilken spillmodus du har valg så vil den da gjøre forskjellige indre valg selv. 

Dersom spill modus er satt til `EASY` så vil koden kalle på private metoden `private fun pickEmptySpot(board: Array<Int>): Int`. Den returner en tilfeldig plassering i som er ledig. Dette er enkel modusen. Det er ingen logikk bak valgene den gjør og derfor veldig enkel å slå.

Hvis spill modus er satt til `HARD` så gjøre litt tyngre logikk, men fremdeles relativt enkel kode. Den kaller da på den private metoden `private fun attackOrDefend(board: Array<Int>, player: Int): Int`. Denne metoden går tilfeldig gjennom rader, koloner og på tvers etter en måte først for å kunne vinne mot spilleren eller om spiller er i ferd med å vinne mot den. Dette gjør den med følgende metoder:
private fun rowOpportunities(board: Array<Int>, player: Int): Int 
private fun columnOpportunities(board: Array<Int>, player: Int): Int
private fun crossOpportunities(board: Array<Int>, player: Int): Int

Dersom den ikke finner en måte å stoppe deg fra og vinne eller en måte for den å vinne så vil den kalle på `EASY` sin metode `private fun pickEmptySpot(board: Array<Int>): Int`. Dette gjør metoden i praksis alltid som første trekk. Dette gjør også at det er mulig å vinne mot den. Det er ikke alltid det er mulig, men i motsetning til `IMPOSSIBLE` så er det relativt ofte mulig å vinne mot den.

Hvis du velger å spille mot `IMPOSSIBLE` så spiller du i praksis mot en MiniMax algoritme. Denne algoritmen sjekker alle mulige trekk opp til brettet er fult allerede på første trekk. Dette gjør at det er umulig å vinne mot den. Hvordan miniMax starter med metoden `private fun miniMax(board: Array<Int>, depth: Int, player: Int)`. Den returnerer`Array<Int>`da den holder på to data. På første plass i arrayet ligger sannsynligheten for at trekket den har gjort er optimalt og på plass nummer to ligger selve trekket. Dette er nødvendig da koden gjør rekursive kall for å optimalisere vinner sannsynligheten sin eller redusere taps resultatet sitt. Den leter altså etter en høyest mulig poengsum og hvis det er umulig for den så vil den lete etter en måte at den ender på en poengsum tilsvarende 0. 

currentScore = miniMax(board, depth - 1, -player)[0]

Inne i metoden gjør den et rekursivt kall på seg selv for å se etter alle mulige utfall. Den lagrer det beste utfallet i den lokale variabler `bestScore` som den sender videre slik at den aldri mister den.

Kode er kommentert dersom det er ønskelig å lese mer om implementasjonen min. Jeg har også prøvd å gjøre navngivingen så tydelig som mulig på alle metoder og lokale variabler. Andre metoder `miniMax` bruker er følgende metoder.
private fun possibleMoves(board: Array<Int>): MutableList<Int>
private fun doWeHaveAWinner(board: Array<Int>): Boolean
private fun setScore(board: Array<Int>): Int
private fun lineScore(firstItem: Int, secondItem: Int, thirdItem: Int): Int
Hver av disse metodene gjør forskjellige sjekker for miniMax algoritmen. 

###### Easter egg
Hvis bruker taster inn rute nummer 4, midt ruta da datamaskinen teller første rute som null for så å taste på rute 2 (oppe til høye) så er det 1/1000 del sannsynlighet for at datamaskinen gjør et tilfeldig trekk. Dette er gjort av metoden `private fun validityOfBoard(board: Array<Int>): Boolean` som ligger helt i bunn av `GameAI` klasse filen. I essens så sjekker den om bruker har tastet inn 42 for så å lage en liste med alle tall fra 1 til 1000. Hvis den da tilfeldig velger tallet 42 så vil `IMPOSSIBLE` algoritmen ikke kjøre `miniMax`, men heller kjøre `pickEmptySpot`metoden. Det er da fremdeles en sannsynlighet for at den velger akkurat det samme valget som den ville ha gjort dersom den ikke gjorde et tilfeldig valg, men det er da en liten sannsynlighet for kunne vinne mot algoritmen.

#### Database
Dette er fulgt oppsett etter hvordan foreleser viste oss å lage en lokal SQLite database i Android. Databasen er satt opp med mulighet for Live data som ikke utnyttes til det fulle,  men det er tenkt at dette en gang kunne blitt en app med ekstern database i fremtiden. Den eneste tabellen som blir tatt med er Spiller tabellen. 

### Arkitektur og flyt
Alle mindre firkanter på tegningen under er Fragmenter og den ene store er aktiviteten. Skjermbilder er vedlagt i mappe under Applikasjon 1. 
(img)

## Krav til applikasjon
#### ☑️ Fragment-arkitektur
Applikasjonen bruker kun en aktivitet ellers gjøres all annen presentasjon til bruker gjennom Fragmenter.

#### ☑️  Bruk av lokal lagring
Applikasjonen lagrer spillere samt Game AI i en spiller database hvor den holder orden på hvor mange poeng hver spiller har og hvor mange ganger de har vunnet. 

#### ☑️  Logikk for å kunne spille alene mot telefonen
Applikasjonen lar deg spille ikke bare mot telefonen, men tre foskjellige type logikker.

### Tillegg
#### Estetikk
Jeg har fager som er gjengiende igjennom hele applikasjoner.  Alle fragmenter presenteres inne i en rute som ligger over bakgrunnen. Rutene i spillet animerer da du trykker på dem. 

Animerende bakgrunn. Jeg brukte mye tid på å få til en fin gradient som skifter mellom tre forskjellige farger. Den animerer mellom disse tre fagene. 

#### Spill AI 
`IMPOSSIBLE` implementasjonen er umulig å slå (med unntak av easter egget).

### Styrker
Koden er veldig konsis og oversiktlig. Jeg har jobbet hardt for å få til en god flyt og en klar kodebase hvor alle linjer betyr noe. 

Impossible modusen på AI spilleren er godt skrevet. Jeg hadde opprinnelig den i en egen tråd for å unngå at den påvirket GUI tråden, men da jeg gjorde en rekke optimaliseringer i koden så ble det unødvendig. Jeg har også fått en god nok forståelse av min egen kode og hvordan MiniMax algoritmen fungerer til at jeg kunne “ødelegge” for den med å legge inn et Easter Egg. 

### Svakheter
Designet i appen er ikke konsistent nok med Temaet Hitchhiker’s Guide to the galaxy. Før jeg hadde bestemt meg for tema så hadde jeg bestemt meg for å få til en animerende gradient bakgrunn. Designet i seg selv er relativt konsistent, men det føles ikke som en klar sammenheng mellom det å film/bok temaet jeg har valg. Hadde jeg vært flink i Photoshop eller andre liknende verktøy for å generere bilder og andre drawble elementer så hadde jeg ville redesignet en hel del. 

Jeg vil riktig nok si at de elementene som er relatert til Hitchhiker’s Guide er ganske godt implementert. Jeg liker musikken, Easter egget med tallet 42, navnene på AI motstanderne, teksten om de tre Ai-ene og selvsagt splash screenen i oppstarten av programmet. Alt det er ganske gøy, det er bare de grafiske elementene jeg skulle ønske hadde en klarere sammenheng. 
# Consola de Juegos

Proyecto desarrollado en **Java** para la asignatura de **Programación Orientada a Objetos (POO)**. Consiste en una consola interactiva que permite ejecutar distintos juegos de texto y consultar un **Top 3 de récords** para cada uno.

## Integrantes

- [Agregar nombres de integrantes y sus aportes]

## Juegos incluidos

| Juego | Descripción | Criterio del récord |
| --- | --- | --- |
| **Palabra Oculta** | Dos participantes: uno establece una palabra y el otro intenta adivinarla antes de agotar seis errores. | 100 puntos por ganar; el adivinador recibe además 10 puntos por cada intento restante. Se conserva la mejor puntuación de cada jugador. |
| **Duelo X-O** | Dos participantes colocan X y O por turnos en un tablero de 3 × 3. | 3 puntos por victoria, 1 por empate y 0 por derrota; los puntos se acumulan por jugador. |
| **Cripta de los Espejos Voraces** | Un explorador se mueve por una cripta, interactúa con espejos y trata de escapar. | El menor número de movimientos en una partida ganada. |

## Requisitos

- **JDK 21** o una versión compatible posterior.
- Un entorno para ejecutar Java, como **IntelliJ IDEA**, o una terminal.
- No se requiere una interfaz gráfica.

## Ejecución

**Desde IntelliJ IDEA:**

1. Abrir la carpeta del proyecto.
2. Configurar el JDK y marcar `src` como carpeta de código fuente si fuese necesario.
3. Ejecutar `src/consola/Main.java` (método `main`).
4. Elegir **Lanzar juego**, **Ver estadísticas** o **Salir**.

**Desde una terminal**, situándose en la carpeta principal del proyecto:

```bash
javac -encoding UTF-8 -d out -sourcepath src src/consola/Main.java
java -cp out consola.Main
```

## Estructura del proyecto

```text
src/
├── consola/
│   └── Main.java
├── games/
│   ├── Jugable.java
│   ├── palabraoculta/
│   │   └── PalabraOculta.java
│   ├── dueloxo/
│   │   └── DueloXO.java
│   └── criptavoraz/
│       └── [clases del juego Cripta]
└── estadisticas/
    ├── Registro.java
    └── GestorEstadisticas.java

records/
├── top_palabraoculta.txt
├── top_dueloxo.txt
└── top_criptavoraz.txt
```

## Programación orientada a objetos e interfaces

La interfaz `games.Jugable` declara `void Start()`. Cada juego la implementa, permitiendo al menú ejecutar distintas clases mediante una referencia del tipo `Jugable`:

```java
Jugable juego = new PalabraOculta(teclado, gestor);
juego.Start();
```

Esto aplica **interfaces y polimorfismo**. También se emplea **encapsulamiento** mediante atributos privados y métodos de acceso en las clases correspondientes.

Para integrar otro juego se debe crear su paquete dentro de `games`, implementar `Jugable` y registrar la nueva opción en el menú de `Main.java`.

## Sistema de estadísticas

La clase `Registro` representa un nombre y una puntuación. `GestorEstadisticas` administra los resultados de cada juego, los ordena según su criterio y muestra los **tres mejores** mediante el menú **Ver estadísticas**.

- **Palabra Oculta:** mayor puntuación es mejor.
- **Duelo X-O:** mayor puntuación acumulada es mejor.
- **Cripta de los Espejos Voraces:** menor cantidad de movimientos en una victoria es mejor.

El sistema **crea tres registros ficticios iniciales por juego** si aún no existe su archivo de puntuaciones. De esta forma, el Top 3 se puede revisar sin jugar previamente tres partidas. Los resultados posteriores se guardan en archivos de texto dentro de `records/` y se recuperan al reiniciar el programa.

Los archivos `records/top_*.txt` pueden incluirse en el repositorio para demostrar los récords precargados; **no** deben excluirse en `.gitignore`.

## Pruebas sugeridas

- Entrar y salir de cada juego desde el menú principal.
- Probar una victoria, derrota o empate según el juego.
- Comprobar que el resultado se registra y aparece en el ranking cuando corresponde.
- Reiniciar la consola y verificar que las puntuaciones siguen guardadas.
- Verificar que las puntuaciones se ordenan correctamente, incluida la clasificación ascendente por movimientos en Cripta.


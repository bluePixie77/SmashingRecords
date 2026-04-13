package gui.smashRecPantallas;

import gui.smashRecColors.Colors;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * Panel de varias páginas de tarjetas (cards) en 2D.
 * Cada página muestra un conjunto de tarjetas organizadas en filas y columnas.
 * Permite avanzar/despáginar, seleccionar carta y visualización de tarjetas
 * basadas en {@link Card} y sus subtipos ({@code ALBUM}, {@code CONCERT}).
 *
 * @author SmashRecords
 */
public class PagedCard2D extends PApplet{

    /**
     * Datos de cada tarjeta organizados como filas de atributos.
     */
    String[][] cardsData;
    /**
     * Array de tarjetas gráficas que se van a mostrar.
     */
    public Card[] cards;
    /**
     * Tipo de tarjeta que maneja este panel (ALBUM, CONCERT, etc.).
     */
    Card.tipoCard tipo;
    /**
     * Número total de tarjetas disponibles.
     */
    int numCards;
    /**
     * Número de filas de tarjetas por página.
     */
    int numRowsPage;
    /**
     * Número de columnas de tarjetas por página.
     */
    int numCardsRow;
    /**
     * Número de tarjetas por página (filas × columnas).
     */
    int numCardsPage;

    /**
     * Página actual (índice empezando en 0).
     */
    int numPage;
    /**
     * Número total de páginas calculadas a partir de {@link #numCards} y {@link #numCardsPage}.
     */
    int numTotalPages;

    // Colores
    /**
     * Esquema de colores de la aplicación.
     */
    public Colors appColors;
    /**
     * Color de fondo del panel de tarjetas.
     */
    int bg;
    /**
     * Color para títulos de tarjetas.
     */
    int titles;
    /**
     * Color para texto normal.
     */
    int text;
    /**
     * Color claro (blanco/claro) para elementos de resalte.
     */
    int white;

    /**
     * Coordenada X superior‑izquierda del área de tarjetas.
     */
    public float x;
    /**
     * Coordinada Y superior‑izquierda del área de tarjetas.
     */
    public float y;
    /**
     * Anchura total del área de tarjetas.
     */
    public float w;
    /**
     * Altura total del área de tarjetas.
     */
    public float h;
    /**
     * Anchura de cada tarjeta individual.
     */
    float wc;
    /**
     * Altura de cada tarjeta individual.
     */
    float hc;
    /**
     * Índice de la tarjeta seleccionada (-1 si ninguna).
     */
    public int selectedCard = -1;


    /**
     * Constructor de un panel paginado de tarjetas.
     *
     * @param p5 referencia a la instancia de Processing (PApplet) asociada.
     * @param appColors conjunto de colores de la aplicación.
     * @param numRows número de filas por página.
     * @param numCols número de columnas por página.
     * @param tipo tipo de tarjeta que se mostrará (ALBUM, CONCERT, etc.).
     */
    public PagedCard2D(PApplet p5, Colors appColors, int numRows, int numCols, Card.tipoCard tipo) {
        this.appColors = appColors;
        this.tipo = tipo;
        this.numRowsPage = numRows;
        this.numCardsRow = numCols;
        this.numCardsPage = numRows * numCols;
        this.numPage = 0;
    }

    /**
     * Establece las dimensiones del panel de tarjetas y calcula automáticamente
     * el tamaño de cada tarjeta teniendo en cuenta los márgenes horizontales y verticales.
     *
     * @param x coordenada X superior‑izquierda.
     * @param y coordenada Y superior‑izquierda.
     * @param w anchura total del panel.
     * @param h altura total del panel.
     */
    public void setDimensions(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.wc =( w - 5*(numCardsRow-1)) / numCardsRow;
        this.hc = (h - 5*(numRowsPage -1)) / numRowsPage;
    }

    /**
     * Asigna los datos de las tarjetas (matriz de atributos) y calcula el número total
     * de tarjetas y el número total de páginas.
     *
     * @param d matriz de datos donde cada fila corresponde a una tarjeta.
     */
    public void setData(String[][] d) {
        this.cardsData = d;
        this.numCards = d.length;
        this.numTotalPages = d.length / this.numCardsPage;
    }

    /**
     * Actualiza los colores del panel usando el esquema de la aplicación.
     *
     * @param appColors conjunto de colores de la aplicación.
     */
    public void setColors(Colors appColors){
        bg = appColors.getFourthColor();
        titles = appColors.getFirstColor();
        text = appColors.getFourthColor();
        white = appColors.getThirdColor();
    }

    /**
     * Crea y posiciona las tarjetas gráficas según {@link #tipo}.
     * Se instancian {@link AlbumCard} o {@link ConcertCard} según el tipo,
     * y se asignan dimensiones y colores.
     */
    public void setCards() {

        cards = new Card[numCards];

        for(int numCard=0; numCard<cardsData.length; numCard++){

            int nr = (numCard / numCardsRow) % numRowsPage;
            int nc = numCard % numCardsRow;

            float yCard = y + (hc + 5) * nr;
            float xCard = x + (wc + 5)* nc;
            switch(tipo){
                case ALBUM:
                    cards[numCard] = new AlbumCard(cardsData[numCard]);
                    break;
                case CONCERT:
                    cards[numCard] = new ConcertCard(cardsData[numCard]);
            }
            cards[numCard].setDimensions(xCard, yCard, wc, hc, 10);
            cards[numCard].setCardColors(appColors.getFirstColor(), appColors.getSecondColor(), appColors.getThirdColor(), appColors.getFourthColor());
        }
    }

    /**
     * Asigna un array de tarjetas ya creadas (por ejemplo para estadísticas).
     * Recalcula el número total de páginas basado en {@link #numCardsPage}.
     *
     * @param objetosGraficos array de tarjetas gráficas.
     */
    public void setCards(Card[] objetosGraficos) {
        this.cards = objetosGraficos;
        this.numCards = objetosGraficos.length;

        // Calculamos el total de páginas basado en el número de objetos
        // (numCards - 1) para evitar que si tienes 3 cartas en 1x1 te diga que hay 4 páginas
        this.numTotalPages = (this.numCards - 1) / this.numCardsPage;
    }

    /**
     * Establece imágenes fijas para tarjetas alternando entre dos imágenes.
     * Las tarjetas en posiciones pares usan {@code img1} y las impares {@code img2}.
     *
     * @param img1 primera imagen a alternar.
     * @param img2 segunda imagen a alternar.
     */
    public void setImages(PImage img1, PImage img2) {
        PImage img;
        for(int numCard=0; numCard<numCards; numCard++){
            if(numCard % 2 ==0){
                img = img1;
            }else{
                img = img2;
            }
            if (cards[numCard] != null) {
                cards[numCard].setImage(img);
            }
        }
    }

    /** Incrementa el índice de página actual si existe una página siguiente disponible. */
    public void nextPage() {
        if (this.numPage<this.numTotalPages) {
            this.numPage++;
        }
    }

    /** Decrementa el índice de página actual si no se está ya en la primera página. */
    public void prevPage() {
        if (this.numPage>0) {
            this.numPage--;
        }
    }

    /**
     * Renderiza en pantalla las tarjetas correspondientes a la página activa.
     * <p>
     * El funcionamiento consiste en calcular el rango de índices (desde {@code firstCardPage}
     * hasta {@code lastCardPage}) que pertenecen a la página actual. Solo los objetos dentro
     * de ese rango invocan su propio método {@code display}. Adicionalmente, dibuja un texto
     * informativo indicando el número de página actual sobre el total.
     * </p>
     * * @param p5 El contexto de Processing donde se realiza el dibujo.
     */
    public void display(PApplet p5) {

        p5.pushStyle();

        // Dibuja Cards correspondiente a la página
        int firstCardPage = numCardsPage*numPage;
        int lastCardPage  = numCardsPage*(numPage+1) - 1;

        for(int numCard=0; numCard<cards.length; numCard++) {
            if(numCard>=firstCardPage && numCard<= lastCardPage) {
                if (numCard < this.numCards && cards[numCard] != null) {
                    cards[numCard].display(p5, numCard == this.selectedCard);
                }
            }
        }

        // Información de la página
        p5.fill(0);
        p5.text("Pag: "+(this.numPage+1)+" / "+(this.numTotalPages+1), x + w + 50, y+10);

        p5.popStyle();
    }

    /**
     * Verifica si el usuario ha hecho clic sobre alguna de las tarjetas visibles.
     * <p>
     * Itera exclusivamente sobre las tarjetas de la página actual. Si detecta que las coordenadas
     * del ratón están sobre una tarjeta (mediante {@code mouseOver}), actualiza {@code selectedCard}
     * con el índice correspondiente. Si el clic ocurre fuera de cualquier tarjeta, se resetea
     * la selección a -1.
     * </p>
     * * @param p5 El contexto de Processing para obtener las coordenadas del ratón.
     */
    public void checkCardSelection(PApplet p5){
        boolean selected = false;
        int firstCardPage = numCardsPage*numPage;
        int lastCardPage  = numCardsPage*(numPage+1) - 1;
        for(int numCard=0; numCard<numCards; numCard++){
            if (numCard >= firstCardPage && numCard <= lastCardPage) {
                if (numCard < cards.length && cards[numCard] != null && cards[numCard].mouseOver(p5)) {
                    selectedCard = numCard;
                    selected = true;
                    break;
                }
            }
        }
        if(!selected){
            selectedCard = -1;
        }
    }

    /**
     * Indica si el cursor del ratón se encuentra actualmente sobre alguna tarjeta de la página activa.
     * * @param p5 El contexto de Processing.
     * @return {@code true} si el ratón colisiona con una tarjeta visible; {@code false} en caso contrario.
     */
    public boolean checkMouseOver(PApplet p5){
        int firstCardPage = numCardsPage*numPage;
        int lastCardPage  = numCardsPage*(numPage+1) - 1;

        for(int numCard=0; numCard<numCards; numCard++){
            if(numCard>=firstCardPage && numCard<= lastCardPage) {
                if (numCard < this.numCards && cards[numCard] != null && cards[numCard].mouseOver(p5)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Muestra en una zona específica de la pantalla la información detallada de la tarjeta seleccionada.
     * * @param p5 El contexto de Processing.
     */
    public void printSelectedCard(PApplet p5){
        if(selectedCard !=-1){
            Card cSelected = cards[selectedCard];
            p5.pushStyle();
            p5.fill(0); p5.textSize(18);
            p5.text("Seleccionada: ", 900, 300);
            p5.textSize(24);
            p5.text(cSelected.title, 900, 340);
            p5.popStyle();
        }
    }
}
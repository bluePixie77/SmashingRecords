package gui.smashRecPantallas;

import processing.core.PApplet;
import gui.smashRecColors.Colors;

public class PopUp {

    float x, y, w, h;
    String title, message;
    public Button bAceptar, bCancelar;
    float buttonW = 200, buttonH = 60;
    public boolean visible = false;

    public PopUp(PApplet p5, Colors appColors, String title, String message, float x, float y, float w, float h) {
        this.title = title;
        this.message = message;
        this.x = x; this.y = y;
        this.w = w; this.h = h;
        float bY = y + h - buttonH * 1.5f;
        this.bAceptar  = new Button(p5, appColors, "Eliminar", x + w/2 - buttonW - 10, bY, buttonW, buttonH);
        this.bCancelar = new Button(p5, appColors, "Cancelar", x + w/2 + 10,           bY, buttonW, buttonH);
    }

    public void setVisible(boolean b) {
        this.visible = b;
        bAceptar.setEnabled(b);
        bCancelar.setEnabled(b);
    }

    public void display(PApplet p5) {
        if (!visible) return;
        float b = 40;
        p5.pushStyle();
        p5.stroke(0); p5.strokeWeight(2); p5.fill(40, 40, 40);
        p5.rect(x, y, w, h, b / 2);
        p5.line(x, y + 2*b, x + w, y + 2*b);
        p5.fill(255, 100, 0); p5.textSize(38); p5.textAlign(p5.LEFT);
        p5.text(title, x + b, y + 1.4f * b);
        p5.fill(255); p5.textSize(24); p5.textAlign(p5.CENTER);
        p5.text(message, x + w/2, y + 4*b);
        bAceptar.display(p5);
        bCancelar.display(p5);
        p5.popStyle();
    }
}
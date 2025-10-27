package gui.smashRecPantallas;

import processing.core.PApplet;

public class PasswordField extends TextField {
    public PasswordField(PApplet p5, int x, int y, int w, int h) {
        super(p5, x, y, w, h);
    }

    // Dibuixa el Camp de Text
    public void display(PApplet p5) {
        p5.pushStyle();
        if (selected) {
            p5.fill(selectedColor);
        } else {
            p5.fill(bgColor);
        }

        p5.strokeWeight(borderWeight);
        p5.stroke(borderColor);
        p5.rect(x, y, w, h, 5);

        p5.fill(fgColor);
        p5.textSize(textSize); p5.textAlign(p5.LEFT, p5.CENTER);
        String password = "";
        for(int i=0; i<text.length(); i++){
            password += "*";
        }
        p5.text(password, x + 5, y + h - textSize);
        p5.popStyle();
    }
}

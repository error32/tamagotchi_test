import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class tamagotchi_test extends PApplet {


float noise=0;
float speed= .015f;
float intSpeed = 0.5f;
float noiseScale=0.02f;
// int side= (int)random(10,20); //g\u00e9rer le nombre de edge/pics
float rayon=200; //taille de stain
float rayonProtected = 40; //si on veut une forme "plus ronde" il faut changer cette valeur vers le haut


boolean random=true;
boolean debug=true;
boolean mvt=true;
boolean save=false;//attention va enregistrer une sequence d'image si save = true en .tif
float time=0;

boolean timeToRandomNow = false;

//New valeurs tamago
int temperature;
int son;
int pollution;

//Pour les besoin de couleur random
float red;
float green;
float blue;


ArrayList<polygon> polygons;

public void setup() {
  size(500, 500);
  frameRate(29);
  smooth();

  polygons = new ArrayList<polygon>();
  polygons.add(new polygon( (int)random(10,20) ));
}


public void draw() { 


  println(time);
  println(polygons.get(0).side);
  time++;
  background(15,15,15);

  translate(width/2, height/2);  
  if(mvt){
    polygons.get(0).shake().trace();
     
 noStroke();     
 fill(255); //yeux
 ellipse(-25,-30,20,15);//G\u00e8re le random pour lui donner peur
 ellipse(20,-30,20,15);//Enfin, g\u00e9rer la taille des yeux quoi ...
 
 fill(0); //couleur pupille
 ellipse(-25,-30,8,2);
 ellipse(20,-30,8,2);
 

float rbouche;
rbouche = random(15,17);
 fill(255); //bouche
 ellipse(0,rbouche,45,20);
 fill(255,191,27);
 ellipse(0,(rbouche)+7,45,20);

  
  }else{
     polygons.get(0).trace();
   }
   
/*  if(time==100){
      side= 10;
  }
  
 if(time==600){
      side= random(400);
  }
  
     if(time>300){
      side= random(1,200);
  }
   
    if(time>400){
      side= random(1,200);
  }
  */
  //Pour le moment inutile, je n'arrive pas \u00e0 le faire changer de forme
  
    if(time==120){
    //polygons.get(0).updatePoint(polygons.get(0).points.size()-1, 500, 80, -PI/12);
    
  }
  if(save){
    saveFrame(); 
  }
if( timeToRandomNow ) {
  polygons.get(0).update((int)random(50,100));
  timeToRandomNow = false;
}

}


public void keyPressed() {
 if (key == 'r' || key == 'R') {
      timeToRandomNow = true;
    }
}
public float fct(float x){
  return noise(x);
}

class polygon {
  int side;
  float bezier;

  ArrayList<pts> points;

  polygon( int s ) {
    side = s;
    bezier = rayon * 2 / side;

    points = new ArrayList<pts>(); 

    for (float i=0; i < side; i++) {
     float angle = i*2*PI/side;
      points.add(new pts(rayon, angle));
    }
    println(2*PI);
  }

  public polygon shake() {
    for (int i=0; i<points.size(); i++) {
      points.get(i).shake();
    }
    
    return this;
  }
  
  public polygon updatePoint(int indice, float time, float newRayon, float newAngle) {
    pts point = this.points.get(indice);
    point.r = newRayon;
    point.a = newAngle;
  
    
   return this;
  }

  public polygon trace() {
    float r, d, x0, y0, x1, y1, d0, r0, x_bezier_1, y_bezier_1, x2, y2, r2, d2, x_bezier_2, y_bezier_2;

    beginShape();
    r0=this.points.get(0).a;
    d0=this.points.get(0).r;
    x0= d0 * cos(r0);
    y0= d0 * sin(r0);
    vertex(x0, y0);
    for (int i=0; i<this.points.size(); i++) {

      r=this.points.get(i).a;
      d=this.points.get(i).r;
      x1= d * cos(r);
      y1= d * sin(r);

      x_bezier_1= x1 + bezier * cos(r + PI/2);
      y_bezier_1= y1 + bezier * sin(r + PI/2);

      if (i+1<this.points.size()) {
        r2=this.points.get(i+1).a;
        d2=this.points.get(i+1).r;
      }
      else {
        r2=this.points.get(0).a;
        d2=this.points.get(0).r;
      }
      x2= d2 * cos(r2);
      y2= d2 * sin(r2);

      x_bezier_2= x2 + bezier * cos(r2 - PI/2);
      y_bezier_2= y2 + bezier * sin(r2 - PI/2); 

      stroke(0);
      bezierVertex(x_bezier_1, y_bezier_1, x_bezier_2, y_bezier_2, x2, y2);

      /*if (debug) {
        //trace tangente
        strokeWeight(1);
        stroke(255, 0, 0);
        line(x1, y1, x_bezier_1, y_bezier_1);
        line(x2, y2, x_bezier_2, y_bezier_2);

        strokeWeight(4);
        point(x_bezier_1, y_bezier_1);
        point(x_bezier_2, y_bezier_2);
        
        //trace structure
        strokeWeight(1);
        stroke(0,0,255);
        line(0,0,x1,y1);
        
      }*/
      
    }
    if (debug) {
      
      red=random(0,255);
      green=random(0,255);
      blue=random(0,255);
       noStroke(); 
      strokeWeight(0.5f);
      //stroke(0);
      fill(255,191,27); //l'humeur peut aussi \u00eatre g\u00e9r\u00e9r par la couleur par la suite (red,green,blue)
    }
    else {
      strokeWeight(1);
      stroke(0);
      fill(0);
    }

    endShape(CLOSE);
    
    return this;
  }

  class pts {
    float r, a;

    float xof=noise;

    public void shake() {
      xof=xof+speed;
      r=rayonProtected+fct(xof)*(rayon-rayonProtected);
     
    }

    pts(float _r, float _a) {

      if (random) {
        noise=random(0, 100);
      }
      else {
        noise=noise+intSpeed;
      }

      r=_r;
      a=_a;
    }
  }
  public void update( int s ){
    side = s;
    points = new ArrayList<pts>(); 

   for (float i=0; i < side; i++) {
     float angle = i*2*PI/side;
      points.add(new pts(rayon, angle));
    }
 
  }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "tamagotchi_test" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}

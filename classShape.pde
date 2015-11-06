float fct(float x){
  return noise(x);
}


class polygon {

  ArrayList<pts> points;

  polygon() {
    points = new ArrayList<pts>(); 

    for (float i=0; i < side; i++) {
     float angle = i*2*PI/side;
      points.add(new pts(rayon, angle));
    }
    println(2*PI);
  }

  polygon shake() {
    for (int i=0; i<points.size(); i++) {
      points.get(i).shake();
    }
    
    return this;
  }
  
  polygon updatePoint(int indice, float time, float newRayon, float newAngle) {
    pts point = this.points.get(indice);
    point.r = newRayon;
    point.a = newAngle;
  
    
   return this;
  }

  polygon trace() {
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
      strokeWeight(0.5);
      //stroke(0);
      fill(255,191,27); //l'humeur peut aussi être gérér par la couleur par la suite (red,green,blue)
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

    void shake() {
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
}
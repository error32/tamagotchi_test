float bezier;
float noise=0;
float speed= .015;
float intSpeed = 0.5;
float noiseScale=0.02;
int side= (int)random(600,800); //gérer le nombre de edge/pics
float rayon=200; //taille de stain
float rayonProtected = 75; //si on veut une forme "plus ronde" il faut changer cette valeur vers le haut


boolean random=true;
boolean debug=true;
boolean mvt=true;
boolean save=false;//attention va enregistrer une sequence d'image si save = true en .tif
float time=0;


//New valeurs tamago
int temperature;
int son;
int pollution;

//Pour les besoin de couleur random
float red;
float green;
float blue;


ArrayList<polygon> polygons;

void setup() {
  bezier = rayon * 2 / side;
  size(500, 500);
  frameRate(29);
  smooth();

  polygons = new ArrayList<polygon>();
  polygons.add(new polygon());
}

void draw() { 
  println(time);
  println(side);
  time++;
  background(15,15,15);

  translate(width/2, height/2);  
  if(mvt){
    polygons.get(0).shake().trace();
     
 noStroke();     
 fill(255); //yeux
 ellipse(-25,-30,20,15);//Gère le random pour lui donner peur
 ellipse(20,-30,20,15);//Enfin, gérer la taille des yeux quoi ...
 
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
  //Pour le moment inutile, je n'arrive pas à le faire changer de forme
  
    if(time==120){
    //polygons.get(0).updatePoint(polygons.get(0).points.size()-1, 500, 80, -PI/12);
    
  }
  if(save){
    saveFrame(); 
  }
}

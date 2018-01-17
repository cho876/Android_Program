void setup() {
  pinMode(8, OUTPUT);
  pinMode(9, INPUT);
  Serial.begin(9600);
}

void loop() {
  digitalWrite(8, LOW);
  delay(1);
  digitalWrite(8, HIGH);
  delay(1);
  digitalWrite(8, LOW);
  
  long a=pulseIn(9, HIGH)/58.2;
  Serial.print("측정된 거리는 : ");
  Serial.print(a);
  Serial.println(" cm입니다.");
  delay(500);
}

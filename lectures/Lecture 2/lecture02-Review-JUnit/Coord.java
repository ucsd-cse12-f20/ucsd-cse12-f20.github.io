
  class Coord {
    public int row, col;
    public Coord(int row, int col) {
      this.row = row;
      this.col = col;
    }
  }

  class Car {
    public String color;
    public Coord location;
    public Car(String color, Coord location) {
      this.color = color;
      this.location = location;
    }
  }

  class Q1 {
    public void g(Car c1, Car c2) {
      c2 = c1;
      c2.color = "blue";
    }
    public String question () {
      Car redCar = new Car("red", new Coord(5, 6));
      Car greenCar = new Car("green", new Coord(7, 8));
      this.g(redCar, greenCar);
      return redCar.color + ", " + greenCar.color;
    }
    public static void main(String[] args) {
      System.out.println(new Q1().question());
    }
  }



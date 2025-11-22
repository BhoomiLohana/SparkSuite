class person {
    private int age;
    private String name, country;

    public String getName(){
        return name;
    }

    public int getAge(){
        return age ;
    }

    public String getCountry(){
        return country;
    }

    public void setName(String n){
        this.name=n;
    }

    public void setAge(int a){
        this.age=a;
    }

    public void setCountry(String c){
        this.country=c;
    }
}



class PTRR {
    public static void main (String []args) {

        person p = new person();
        p.setName("Bhoomi");
        p.setAge(20);
        p.setCountry("PAKISTAN");

        System.out.println("name : "+ p.getName());
        System.out.println("age : "+ p.getAge());
        System.out.println("country : "+ p.getCountry());
    }
}
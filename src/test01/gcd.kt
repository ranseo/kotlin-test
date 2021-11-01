package test01


//a>b
//최대공약수
fun getGCD(a: Int, b:Int) : Int {
    if(b==0) return a;
    else return getGCD(b, a%b)
}


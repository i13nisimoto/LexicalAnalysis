import java.io.*;
import java.lang.Character;
class Main{
  public static void main(String args[])throws Exception{
    String file_name="Main.java";
    File file = new File(file_name);
    BufferedReader br = new BufferedReader(new FileReader(file));
    String str=strAdd(br);
    //System.out.print(str.charAt(3));
    lexer(str);
  }
  public static String strAdd(BufferedReader br)throws Exception{
    String buffer = br.readLine();
    String text = new String();

    while( buffer != null ) {
      text = text.concat( buffer );
      text=text.concat("\n");
      buffer = br.readLine();
    }
    return text;
  }
  public static void lexer(String str){
    int q,bgn;
    for(int i=0,tmp=str.length();i<tmp;){
      if(i+1<tmp&&(str.charAt(i)=='/'&&str.charAt(i+1)=='/')){ //コメント
        for(i+=2;i<tmp&&str.charAt(i)!='\n';i++);
      }else if(i+1<tmp&&str.charAt(i)=='/'&&str.charAt(i+1)=='*'){/*コメント*/
        /*aa
        */
        for(i+=2;i-1<tmp&&str.charAt(i-2)!='*'||str.charAt(i-1)!='/';i++);
      }else if(str.charAt(i)<=' '){                             //空白
          i++;
      }else if((q=str.charAt(i))=='\"'||q=='\''){//"xxx" 'xxx'
        for(bgn=++i;i<tmp&&str.charAt(i)!=q;i++){
          char c=str.charAt(i);
          if(c=='\\'||(0x80<=c&&c<=0x9F)||(0xE0<=c&&c<=0xFF))i++;
        }//escape文字とかをスルー
        setToken(1,str,bgn-1,i+1);
        i++;//後ろをの" 'をスルー
      }else if(Character.isDigit(str.charAt(i))){
        for( bgn=i;i<tmp&&!(isSymbol(str.charAt(i)));){
          char c;
          if((c=str.charAt(i++))=='E'||c=='e')i++;//1.2e-3とかで分離しないようにする(やっつけ)
        }
        setToken(1,str,bgn,i);
      }else if(isAlpha(str.charAt(i))){
        for(bgn=i++;i<tmp&&isAlphaNumeric(str.charAt(i));)i++;
        setToken(1,str,bgn,i);
      }else{
        setToken(1,str,i,i+1);
        i++;
      }
    }
  }
  public static void setToken(int type,String str,int start,int end){
  //  System.out.println(start+" "+end);
    System.out.println(str.substring(start,end));
  }
  public static boolean isAlpha(char c){return (Character.isAlphabetic((int)c)||c=='_');}
  public static boolean isAlphaNumeric(char c){return isAlpha(c)||Character.isDigit(c);}
  public static boolean isSymbol(char c){return c>' '&&!isAlphaNumeric(c);}
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dmitry
 */
public class NSequenceGenerator {
        private int slength=0;
        int lang;
        private int []sequence=null;
       private boolean status=false;;
        /*
         n=2
         lang = 3
         ab
         ac
         bc
         */
        public NSequenceGenerator(int tlang,int tlength)
        {
            init(tlang,tlength);
        }
        public void init(int tlang,int tlength)
        {
         this.slength=tlength;
            this.lang=tlang;
            this.sequence=new int[this.slength];
            for(int i=0;i<slength;i++)
            {
                sequence[i]=i;
            }
            status=true;
        }
        public int[] get()
        {
          int ret[];
          ret=this.sequence.clone();
          return ret;
        }
        public boolean getnext()
        {
          do
          {
              if(!status)return status;
          step(slength-1);
          isstop();
          }while(iseq()||isequprising());
          return status;
        }

    public long  getmax() {
        long ret=0;
        ret=(long)(Math.pow(lang,slength)/2)-lang;
      return ret;
    }
        private void step(int c)
        {
            if(c<0)return;
            if(sequence[c]+1>=lang)
             {
                 sequence[c]=0;
                 step(c-1);
             }else sequence[c]++;
        }
        private boolean iseq()
        {  
           for (int i=0;i<sequence.length-1;i++)
           {
               for (int j=i+1;j<sequence.length;j++)
             if(sequence[i]==sequence[j])return true;
           }
           return false;
        }
         private boolean isequprising()
        {  
           for (int i=0;i<sequence.length-1;i++)
           {
             if(sequence[i]>sequence[i+1])return true;
           }
           return false;
        }
         private boolean isstop()
         {
             int match=0;
          for(int i=0;i<slength;i++)
            {
                if(sequence[i]==i)match++;
            }
             if(match==slength)status=false;
             return status;
         }
        public void printseq()
        {
          for (int j=0;j<sequence.length;j++)System.out.print(sequence[j]+" ");
                    System.out.println();
        }
        public void printseq(int ret[])
        {
          for (int j=0;j<ret.length;j++)System.out.print(ret[j]+" ");
                    System.out.println();
        }
        public long trymax() {
                NSequenceGenerator a=new NSequenceGenerator(lang,sequence.length);
              
                int ret[];
                long cnt=0;
    
                    while(a.getnext())cnt++;
                 
               return cnt;
                
            }
         public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                NSequenceGenerator a=new NSequenceGenerator(504,3);
                a.printseq();
                int ret[];
                long cnt=0;
                long start=System.nanoTime();
                    while(a.getnext())
                    {
                    ret=a.get();
                   // a.printseq(ret);
                    cnt++;
                    }
                System.out.println("cnt "+cnt+" elapsed "+(System.nanoTime()-start));
                
            }
        });
    }
}

package cnnhw1;

import java.io.*;
import java.math.*;
import java.util.*;

public class cnnhw1{ //�ثe�o�ӵ{���X�D�n�����շP����1
//	static ArrayList<ArrayList<String>> array_list = new ArrayList<ArrayList<String>>();
	static ArrayList<float[]> array = new ArrayList<float[]>();
	static Scanner input = new Scanner(System.in);
	
/*
	public static float sub(float s1,float s2){
		BigDecimal b1 = new BigDecimal(Float.toString(s1));
	    BigDecimal b2 = new BigDecimal(Float.toString(s2));
	    return b1.subtract(b2).floatValue();
	} //���B�⪺�ɫ��ȶǶi�ӥ���string�x�s bigdecimal �A��float�ǥX�h
*/
	
	/*	1.load file
	 * 	2.use split and trans to float wiz token2,then store into array
	 * 	3.do cal 
	 * 
	 */
	public static void main(String[] args) throws IOException{
		String Filename = "E:\\1041\\cnn\\HW1\\1.txt";
		FileReader fr = new FileReader(Filename); 
		BufferedReader br = new BufferedReader(fr);//�bbr.ready�Ϭd��J��y�����p�O�_�����

		String txt;
		while((txt=br.readLine())!=null){
			String[] token = txt.split("\t");
			float[] token2 = new float[token.length];//�ŧifloat[]

			try{
				for(int i=0;i<token.length;i++){
					token2[i] = Float.parseFloat(token[i]);	
				}//��token(string)�୼token2(float)
				array.add(token2);//��txt�̭����e�����ιL�b��Ū�iarray�� 
			}catch(NumberFormatException ex){
				System.out.println("Sorry Error...");
			}
		}
		fr.close();//�����ɮ�
		
		System.out.println("Please enter loop times: ");//��J���N���ƭ���
		
		int looptimes = input.nextInt();
		System.out.println("Your loop times is "+ looptimes);
		for(int i=0;i<array.size();i++){
			for(int j=0;j<array.get(i).length;j++){
			System.out.print(array.get(i)[j] + "\t");
			}
			System.out.println();
		}//��arraylist �������e���L�X��
		
		/*
		 * �H�U�}�l���B�� ���]�w�ǲ߲v���l�]�w��ҥ��@��  
		 */
		/*
		 * ���Τ@�Ӱ�ǭ� �p�G>=1 ���N�����ΥL�]��1 ��L���P�]��-1
		 * �P�z �p�G�@�}�l����0or-1 
		 */
		int reference=Float.floatToIntBits(array.get(0)[array.get(0).length-1]);
		for(int i=0;i<array.size();i++){
			if(array.get(i)[array.get(i).length-1]==reference){
				array.get(i)[array.get(i).length-1]=-1;
			}
			else{
				array.get(i)[array.get(i).length-1]=1;
			}		
		}//���ˬO�諸

		System.out.println("start to do cal");
		float studyrate = 0.8f;
		float[] initial={0f,1.0f};
		float threshold= -1.0f;
		int x0=-1;
		float sum=0f;
		float caltemp=0f;
		int judge=0;
		int xn=0;
		int dataamount = array.size();
		int correctcount=0;
		int correctflag = 0;
		whileloop:
		while(looptimes!=0){
			
			for(int w=0;w<(array.get(xn).length-1);w++){//�n�Ϊ��u���e��� �̫�@�ӬOdesire
				sum += (initial[w]) * (array.get(xn)[w]);//���B��ɧ�array����¾���୼float
			}
			caltemp=x0*threshold;
			sum += caltemp;	//�֭ȸ�x0�ۭ��̫�A��
			if(sum>=0){
				judge=1;//sum result > 0
			}
			else{
				judge=-1;//sum result <0
			}
	
			if (judge!=array.get(xn)[array.get(xn).length-1]&&judge>=0){
				for(int w=0;w<(array.get(xn).length-1);w++){//�n�Ϊ��u���e��� �̫�@�ӬOdesire
					initial[w] -= studyrate*array.get(xn)[w];
				}
				threshold -= studyrate*x0;//call sub function �w���򥢺��			
				correctcount=0;
			}
			else if(judge!=array.get(xn)[array.get(xn).length-1]&&judge<0){
				for(int w=0;w<(array.get(0).length-1);w++){//�n�Ϊ��u���e��� �̫�@�ӬOdesire
					initial[w] += studyrate*array.get(xn)[w];	
				}
				threshold += studyrate*x0;
				correctcount=0;
			}
			else{
				correctcount++;
			}
			System.out.println("output threshold & weight");
			System.out.printf("%f\n",threshold);
			for(int w=0;w<initial.length;w++){
				System.out.printf("%f\n",initial[w]);
			}
			
			if(correctcount==dataamount-1){
				correctflag=1;
				break whileloop;
			}//���ͪ���Įɵ��@��correctflag = 1  ���᭱break���᪺�L�i�H�L���T����T
						
			if (xn==dataamount-1){
				xn = 0;
			}
			else{
				xn++;
			}//xn �k�s���Y�}�l��
			looptimes--;//looptime countdown
		}
		if(correctflag==1){
			System.out.println("Stop the loop cuz find a good solution");
		}
		else{
			System.out.println("Sorry, out of looptimes");
		}
	}
}

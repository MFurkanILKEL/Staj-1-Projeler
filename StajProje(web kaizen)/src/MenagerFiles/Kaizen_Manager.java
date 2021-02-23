package MenagerFiles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import DB_Connection.Database_Connection;

public class Kaizen_Manager {
	public static Connection con ;
	public  static String ka�zId="";
	public static String �slemG�ncelleId="";
	public static String �slemDeleteId="";
	
	
	
	 public static String Kaizen_Organizasyon(String P_ORG_ID  ) {
		 
		     Connection con=null;
		        String sorguOrgBilg=null;
		        Statement st=null;
		        ResultSet rs=null;
                
		    	int i=0;
		    	String d�nd�r;
		    
                //burada organizasyondaki �e�itli seviyeler i�in birle�tirme yap�yrum ona g�re sorguyu belirliyorum
		    	if(P_ORG_ID.equals("1") || P_ORG_ID.equals("2") || P_ORG_ID.equals("3") || P_ORG_ID.equals("4") ) {
		    		   sorguOrgBilg ="SELECT *  FROM KAIZEN_ORGANIZASYON_TBL O , KAIZEN_KAIZEN_TBL K  WHERE\r\n" + 
		    		   		"(O.O_ID=K.K_ORG_ID) AND( (O.O_ID=1)OR (O.O_ID=2)OR (O.O_ID=3)OR (O.O_ID=4)) ";
		    		
		    	}
		    	else if(P_ORG_ID.equals("5") || P_ORG_ID.equals("6") || P_ORG_ID.equals("7") || P_ORG_ID.equals("8")) {
		    		   sorguOrgBilg ="SELECT *  FROM KAIZEN_ORGANIZASYON_TBL O , KAIZEN_KAIZEN_TBL K  WHERE\r\n" + 
		    		   		"(O.O_ID=K.K_ORG_ID) AND( (O.O_ID=5)OR (O.O_ID=6)OR (O.O_ID=7)OR (O.O_ID=8)) ";
		    		
		    	}
		    	
		    	
		    	d�nd�r="<table id='�nitem'><tr><th>K ID</th><th>KAIZEN ADI</th><th>KAIZEN KONU</th>"
		         		+ "<th>K BASVR TRH</th><th>KAIZEN ACIKLAMA</th>";
		            
		      
              //1.ko�ul ayn� org kaizenleri getirmek i�in
		        //2. ko�ul ayn� organizasyondaki ki�ileri getirmek i�in
		        //3. ko�ul org �d si belirlemek i�in
		        //burada giri� yapan ki�inin org numaras�n� alarak yan� organizasyonaki kaizen i olan �al��anlar�n bilgilerini ald�m
		        
		 try {
			 con = Database_Connection.getConnection_Kaizen_Db();
			 st = con.createStatement();
		     rs = st.executeQuery(sorguOrgBilg);
			 
		    while (rs.next()) {

		    	
              d�nd�r+="<tr> <td>"+rs.getString("K_ID")+"</td>"+
					       "<td>"+rs.getString("K_ADI")+"</td>"+
					       "<td>"+rs.getString("K_KONU")+"</td>"+
					       "<td>"+rs.getString("K_BASV_TARH")+"</td>"+
					       "<td>"+rs.getString("K_A�IKLAMA")+"</td>"+
					     
					                                                            "</tr>";
                 i++;
		           
		    }
		            d�nd�r+="</table>";
                     System.out.println(d�nd�r);
		} catch (Exception e) {
			// TODO: handle exception
		}

			return d�nd�r;
		}
	 
	 public static String Kaizen_Personel(String P_SICIL ) {
		
		     Connection con=null;
		        String sorguKaizen=null;
		        Statement st=null;
		        ResultSet rs=null;
		        String d�nd�r=null;
		        int i=0;

		    	
		         
		         d�nd�r="<table id='tablo'><tr><th>ID</th><th>ADI</th><th>KONU</th><th>BASVURU TRH</th><th>TAMAMLANMA TRH</th>"
		         		+ "<th>SON HAREKET TRH</th><th>SON HAREKET ID</th><th>RED TARIHI</th>";
		            
		    	 sorguKaizen ="SELECT *  FROM  KAIZEN_KAIZEN_TBL K ,KAIZEN_PERSONEL_TBL P ,KAIZEN_ISLEM_TBL I ,KAIZEN_DURUM_TBL D WHERE "
		    	 		+ "(P.P_SICIL="+P_SICIL+") AND	(I.I_SICIL_ID=P.P_SICIL) AND (I.I_KAIZ_ID=K.K_ID)  AND (D.D_ID=I.I_DURUM_ID)";

		 try {//burada bean sayfalar�na at�yorum
			 con = Database_Connection.getConnection_Kaizen_Db();
			 st = con.createStatement();
		     rs = st.executeQuery(sorguKaizen);
			 
		    while (rs.next()) {
	    d�nd�r+="<tr id='sat�r"+i+"' onclick='yazSayfa("+i+")'>  <td id='KaizenId"+i+"'>"+rs.getString("K_ID")+"</td>"+
						       "<td id='KaizenAd�"+i+"'>"+rs.getString("K_ADI")+"</td>"+
						       "<td id='KaizenKonu"+i+"'>"+rs.getString("K_KONU")+"</td>"+
						       "<td id='KaizenBasvTarh"+i+"'>"+rs.getString("K_BASV_TARH")+"</td>"+
						       "<td id='KaizenTamamTarh"+i+"'>"+rs.getString("K_TAMAM_TARH")+"</td>"+
						       "<td id='KaizenSonHrktTarh"+i+"'>"+rs.getString("K_SON_HARK_TARH")+"</td>"+
						       "<td id='KaizenSonHrktId"+i+"'>"+rs.getString("K_SON_HARK_ID")+"</td>"+
						       "<td id='KaizenRedTarh"+i+"'>"+rs.getString("K_RED_TARH")+"</td>"+
						                                  "</tr>";
				 i++;
		           
		    }
		    d�nd�r+="</table>";
              System.out.println(d�nd�r);
		     
		} catch (Exception e) {
			// TODO: handle exception
		}

			return d�nd�r;
		}


	 
	 
	 public static void Ka�zen_Insert(String P_SICIL , String K_ADI , String K_KONUSU ,String K_SON_HARK_ID , String K_ORG_ID,String K_A��klama ) {
		 
		 
	    	PreparedStatement ps;
	    	String baslang�c,sorguKa�zen;
	    	Date d;
	        SimpleDateFormat ft2;
	    	
	   
	    	//false ile doludur bo� b�rak�l�nca kay�t yapm�yor
	   if(bosMuEkle(P_SICIL, K_ADI, K_KONUSU, K_SON_HARK_ID, K_ORG_ID, K_A��klama)==false) {
	    	 d= new Date();
	         ft2=new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
	         System.out.println(ft2.format(d)) ;
	    	
	    	baslang�c="0001.01.01 00:00:00";
	    	//DAHA SONRA D�ZELTME YAPARKEN KOLAYLIK OLSUN D�YE BURADA TUUTUM BASLANGI�� DE�ER�N�
	    	
	    	con = Database_Connection.getConnection_Kaizen_Db();
	    	 sorguKa�zen="INSERT INTO  KAIZEN_KAIZEN_TBL (K_ADI, K_KONU, K_BASV_TARH, K_TAMAM_TARH, K_SON_HARK_TARH, K_SON_HARK_ID, K_ORG_ID, K_RED_TARH,K_A�IKLAMA,K_P_SICIL) VALUES \r\n" + 
		         		"(?, ?, TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS'), \r\n" + 
		         		"TO_TIMESTAMP(?,'YYYY-MM-DD HH24:MI:SS'),\r\n" + 
		         		"TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS'),\r\n" + 
		         		"?, ?, TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS'),?,?)";
		        
	    	try {
	    		
	    		//buraya �imdiki zaman i�in metot kullan�cam basvuru ve son hareket tarihleri �in 
	    		//kalanlar� 01 olarak g�ndericem 
	    		
	    		  ps = con.prepareStatement(sorguKa�zen);
			      
	    		  System.out.println(K_ADI);
	    		    ps.setString(1, K_ADI);
	    		    System.out.println(K_KONUSU);
			        ps.setString(2, K_KONUSU);
			        System.out.println(ft2.format(d));
			        ps.setString(3, ft2.format(d));
			        System.out.println(baslang�c);
			        ps.setString(4, baslang�c);
			        System.out.println( ft2.format(d));
			        ps.setString(5, ft2.format(d));
			        System.out.println(K_SON_HARK_ID);
			        ps.setString(6, K_SON_HARK_ID);
			        System.out.println(K_ORG_ID);
			        ps.setString(7, K_ORG_ID);
			        System.out.println(baslang�c);
			        ps.setString(8, baslang�c);
			        System.out.println(K_A��klama);
			        ps.setString(9, K_A��klama);
			        System.out.println(P_SICIL);
	                ps.setString(10, P_SICIL);
			       
	    		ps.executeUpdate();
	    		
	    		
	    		JOptionPane.showMessageDialog(null, "kaizen tabosu olu�turuldu");
	    		
	    		
	    		//burada ka�zn �n �d s�n� bulup �slem table ye koyaca��m
	    		//bunu ayn� metotta alt�na i�lem ve durum tablolar�n� doldurabilmek i�in yap�yorum
	    		ka�zIdBul(K_ADI,K_KONUSU,K_A��klama,K_ORG_ID,P_SICIL);
	    		
	    		//burada �slem ve durum tablolar� i�in gereken de�erler at�yorum 
	    		ka�zenIslemTablosu(P_SICIL);
	    		
	    		
			} catch (Exception ex) {
				 JOptionPane.showMessageDialog(null, ex.getMessage());
				// TODO: handle exception
			}
	    	
	   }
	   else {
		   JOptionPane.showMessageDialog(null, "l�tfen bos b�rakmay�n�z");
	   }
	    	
	    	
	 }
	 
	 public static boolean bosMuEkle(String P_SICIL , String K_ADI , String K_KONUSU ,String K_SON_HARK_ID , String K_ORG_ID,String K_A��klama) {
		 boolean dolu=true;
		 if(P_SICIL!=null && !K_ADI.equals("") && !K_KONUSU.equals("") && !K_SON_HARK_ID.equals("")  && !K_ORG_ID.equals("") &&  !K_A��klama.equals("")) {
	        	dolu=false;
	        }
		 return dolu;
	 }
	 //Burada �slem tablosuna gereken itemleri giriyorum
	 //durum tablosuna 1 yan� olu�turuldu durumunu at�yorum
	 public static void ka�zenIslemTablosu(String I_S�c�l) {
		 String sorguIslem;
		 PreparedStatement ps;
       Date d;
       SimpleDateFormat ft2;

		 
		 d= new Date();
       ft2=new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
       System.out.println(ft2.format(d)) ;
       String durum="1";
       System.out.println( ka�zId);
       sorguIslem="INSERT INTO KAIZEN_ISLEM_TBL (I_SICIL_ID, I_KAIZ_ID, I_DURUM_ID, I_OPER_SAAT) VALUES " + 
      	 		"(?, ?, ?, TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS'))";
	    		
     
     try {
      	 
	    		  ps = con.prepareStatement(sorguIslem);
	    		  ps.setString(1, I_S�c�l);
	    		  ps.setString(2, ka�zId);
	    		  ps.setString(3, durum);
	    		  ps.setString(4, ft2.format(d));
	    		  
	    		ps.executeUpdate();
	    		
	    		JOptionPane.showMessageDialog(null, "i�lem tablosu olu�turuldu");
	    		
	    		
			} catch (Exception ex) {
				 JOptionPane.showMessageDialog(null, ex.getMessage());
				// TODO: handle exception
			}
		 
		 
	 }
	 //E�ER BA�VURU TAR�H�N� DE EKLERSEM TAM ANLAMIYLA OLACAK
	 public static void ka�zIdBul(String K_ADI,String K_KONU,String K_A��klama,String K_ORG_ID,String P_SICILARA) {
		 
		 String sorguIdBulma;
		 Statement st;
	        ResultSet rs;
		 
		 //KAIZEN ADINI UN�QUE KOYDUM K� ID SINE ULA�AB�LMEK ���N
		 sorguIdBulma="SELECT * FROM KAIZEN_KAIZEN_TBL WHERE (K_ADI='" + K_ADI + "') AND (K_KONU='" + K_KONU + "') AND "
		 		+ "(K_A�IKLAMA='" + K_A��klama + "') AND (K_ORG_ID='" + K_ORG_ID + "') AND (K_P_SICIL='" + P_SICILARA +"')";
	      try {
			
	    	 st = con.createStatement();
		     rs = st.executeQuery(sorguIdBulma);
		     
		     while (rs.next()) {
		    	
		    	 ka�zId=rs.getString("K_ID");
		    	
		     }

		     
		     
		} catch (Exception e) {
			// TODO: handle exception
		}
	     
	 }

	 
	 
	 
	 
	 
	 
	 //G�NDER�RKEN DE���ECEK OLANI DE���T�R�YORUM (ID DI�INDA)D��ERLER� NULL G�NDER�YORUM
	 //son olarak �slem tablosundaki saati d de�i�tirece�im
	 public static void Ka�zenUpdate( String K_ADI ,String K_KONUSU,String K_ORG_ID , String K_ID,String K_ACIKLAMA) {
		  
		  Connection con;
		  PreparedStatement ps ;
		  String sorguHepsi;
		  
		  
		  
	  
		 con=Database_Connection.getConnection_Kaizen_Db();
		  
		    //false d��yorsa girer
		 if(bosMuG�ncelle( K_ADI, K_KONUSU, K_ORG_ID, K_ID, K_ACIKLAMA)==false) {
			 
			  try {

				  sorguHepsi="UPDATE KAIZEN_KAIZEN_TBL SET  K_ADI=?,K_KONU=?,K_Org_ID=?,K_A�IKLAMA=? WHERE K_ID=?";

	  		  ps = con.prepareStatement(sorguHepsi );
	  		  
	  		  ps.setString(1, K_ADI);
	  		  ps.setString(2, K_KONUSU);
	  		  ps.setString(3, K_ORG_ID);
	  		  ps.setString(4, K_ACIKLAMA);
	  		  ps.setString(5, K_ID);
	  		  
	  		  
	  		ps.executeUpdate();
	  			 System.out.println( "tamamland�");
	  			 
	  			 
	  			
	  			
	  		  
			} catch (Exception ex) {
				 JOptionPane.showMessageDialog(null, ex.getMessage());    		
			}
			   }
		 
		 else {
			 JOptionPane.showMessageDialog(null, "Bo� alan b�rakt�n�z Kay�t  Yap�lmad�");
		 }
		 islemG�ncelle(K_ID);//tarihi g�ncelleyemiyotum daha sonra bakacam
		 
		
		  }
	
	 public static void islemG�ncelle(String K_ID) {
		 
	  ka�zIdBulIslem(K_ID);//kaizen tablosundan �d ve islem  �d sini bulup gllobal at�yorum buluyor
		 
		 String sorguIslem;
		 PreparedStatement ps;
       Date d;
       SimpleDateFormat ft2;

		 
		 d= new Date();
       ft2=new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
       System.out.println("i�lem yap�lan saat"+ft2.format(d)) ;
      System.out.println("islem tablosu �d si"+�slemG�ncelleId);
       //burada buldu�um �d yi kullan�yorum
       System.out.println( "g�ncellenecek Id"+ka�zId);
       sorguIslem="UPDATE KAIZEN_ISLEM_TBL SET I_OPER_SAAT = TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS.FF') WHERE I_ID=?";
	    		
    
     try {
        		  ps = con.prepareStatement(sorguIslem);
			       
        		  ps.setString(1, ft2.format(d));
        		  ps.setString(2, �slemG�ncelleId);
        		 
	    		ps.executeUpdate();
	    		
	    	
	    		
	    		
			} catch (Exception ex) {
				 JOptionPane.showMessageDialog(null, ex.getMessage());
				// TODO: handle exception
			}
		 
		 System.out.println("��lem saati g�ncelendi");
	 }
	 

	
	 public static void ka�zIdBulIslem(String K_ID ) {
		 
		 String sorguIdBulma;
		 Statement st;
	        ResultSet rs;
	        System.out.println();
	        con=Database_Connection.getConnection_Kaizen_Db();
		 //KAIZEN ADINI UN�QUE KOYDUM K� ID SINE ULA�AB�LMEK ���N
		 sorguIdBulma="SELECT * FROM KAIZEN_KAIZEN_TBL K,KAIZEN_ISLEM_TBL I WHERE  (K.K_ID="+K_ID+") AND (K.K_ID=I.I_KAIZ_ID)";
	      try {
			
	    	 st = con.createStatement();
		     rs = st.executeQuery(sorguIdBulma);
		     while (rs.next()) {
		    	
		    	 ka�zId=rs.getString("K_ID");
		    	 �slemG�ncelleId=rs.getString("I_ID");
		     }

		} catch (Exception e) {
			// TODO: handle exception
		}
	      }

	 public static boolean bosMuG�ncelle( String K_ADI ,String K_KONUSU,String K_ORG_ID , String K_ID,String K_ACIKLAMA) {
    boolean sonuc=true;
	if( !K_ADI.equals("") && !K_KONUSU.equals("") && !K_ORG_ID.equals("") && !K_ID.equals("")&& !K_ACIKLAMA.equals("") ) {
		sonuc=false;
	}
	return sonuc;
}
	 




      
	 public static String Ka�zen_Detay(String P_S�C�L,String K_ID) {
		
		     
		     Connection con;
		        String sorguKaizen,detay="",kontrol="";
		        Statement st;
		        ResultSet rs;

		    	
		    	
		    	
		            
		    	 sorguKaizen ="SELECT * FROM KAIZEN_KAIZEN_TBL K, KAIZEN_PERSONEL_TBL P,KAIZEN_ISLEM_TBL I,KAIZEN_DURUM_TBL D\r\n" + 
		    	 		"WHERE (P.P_SICIL="+P_S�C�L+") AND (I.I_SICIL_ID=P.P_SICIL) AND (I_KAIZ_ID=K.K_ID)AND (D.D_ID=I_DURUM_ID)\r\n" + 
		    	 		"AND (K.K_ID="+K_ID+")";

		 try {
			 con = Database_Connection.getConnection_Kaizen_Db();
			 st = con.createStatement();
		     rs = st.executeQuery(sorguKaizen);
			 
		    while (rs.next()) {
		    	kontrol=rs.getString("P_ADI");
		    	
		    	detay+="<p class='label'>Personel Id</p>"
		    			 +"<p id='perId'>"+rs.getString("P_ID")+"</p>"
		    			 +"<p class='label'>Personel Sicil</p>"
		    			+"<p id='perSicil'>"+rs.getString("P_S�C�L")+"</p>"
		    					 +"<p class='label'>Personel Adi</p>"
		    			+"<p id='perAd'>"+rs.getString("P_ADI")+"</p>"
		    					 +"<p class='label'>Personel Soyadi</p>"
		    			+"<p id='perSoyad'>"+rs.getString("P_SOYADI")+"</p>"
		    					 +"<p class='label'>Son Islem Tarih</p>"
		    			+"<p id='islemOper'>"+rs.getString("I_OPER_SAAT")+"</p>"
		    					 +"<p class='label'>Kaizen Id</p>"
		  				+"<p id='kaizId'>"+rs.getString("K_ID")+"</p>"
		  						 +"<p class='label'>Kaizen Adi</p>"
		    			+"<p id='kaizAd'>"+rs.getString("K_ADI")+"</p>"
		    					 +"<p class='label'>Kaizen Konu</p>"
		    			+"<p id='kaizKonu'>"+rs.getString("K_KONU")+"</p>"
		    					 +"<p class='label'>Ba�vuru Tarihi</p>"
		    			+"<p id='kaizBasv'>"+rs.getString("K_BASV_TARH")+"</p>"
		    					 +"<p class='label'>Tamamlanma Tarihi</p>"
		    			+"<p id='kaizTama'>"+rs.getString("K_TAMAM_TARH")+"</p>"
		    					 +"<p class='label'>Son Hareket Tarihi</p>"
		    			+"<p id='kaizSonTrh'>"+rs.getString("K_SON_HARK_TARH")+"</p>"
		    					 +"<p class='label'>Son Hareket Id</p>"
		         		+"<p id='kaizSonId'>"+rs.getString("K_SON_HARK_ID")+"</p>"
		         				 +"<p class='label'>Organizasyon Bilgisi</p>"
		    		    +"<p id='kaizOrg'>"+rs.getString("K_ORG_ID")+"</p>"
		    		    		 +"<p class='label'>Red Tarihi</p>"
		        		 +"<p id='kaizRed'>"+rs.getString("K_RED_TARH")+"</p>"
		        				 +"<p class='label'>Kaizen Son Durum</p>"
		    	    	 +"<p id='durumAd'>"+rs.getString("D_ADI")+"</p>"
		    	    			 +"<p class='label'>Kaizen A��klama</p>"
		    	    	 +"<p id='kaizA��klama'>"+rs.getString("K_A�IKLAMA")+"</p>";
		    			
		           
		    }
		    if(kontrol.equals("")) {
		    	JOptionPane.showMessageDialog(null, "l�tfen tabloda buluna �d numaralar�ndan giriniz");
		    	
		    }
		          
		     
		} catch (Exception e) {
			// TODO: handle exception
		}
	return detay;
	}
	
	 
	 
	 
	 
		public static void Ka�zen_Delete( String K_ID ) {
			 
			Ka�zIdbULDelte(K_ID);
			   
		 	PreparedStatement ps1,ps2;
		 	String sorguKa�zen,sorguIslem;
		 	
		 	
		    
			   con=Database_Connection.getConnection_Kaizen_Db();
		 	 sorguKa�zen="DELETE FROM KAIZEN_KAIZEN_TBL WHERE K_ID=?";
		 	 sorguIslem="DELETE FROM KAIZEN_ISLEM_TBL WHERE I_ID=?";
		 	 
			        
		 	try {
		 		//�nce islemi sildm ki fk ile ba�lant�s� s�k�nt� ��karm�s�n diye
		 		ps1 = con.prepareStatement(sorguIslem);
		 		  ps2 = con.prepareStatement(sorguKa�zen);
				       
		 		ps1.setString(1,�slemDeleteId );
		 		ps2.setString(1, ka�zId);
		 		ps1.executeUpdate();
		 		ps2.executeUpdate();
		 		
		 		
		 		
		 		
				} catch (Exception ex) {
					 JOptionPane.showMessageDialog(null, ex.getMessage());
					// TODO: handle exception
				}
		 	}

	 public static void Ka�zIdbULDelte(String K_ID) {
	     	
		   con=Database_Connection.getConnection_Kaizen_Db();
			  
		 String sorguIdBulma;
		 Statement st;
	       ResultSet rs;
		 
		 //KAIZEN ADINI UN�QUE KOYDUM K� ID SINE ULA�AB�LMEK ���N
		 sorguIdBulma="SELECT * FROM KAIZEN_KAIZEN_TBL K, KAIZEN_ISLEM_TBL I WHERE \r\n" + 
		 		"(K.K_ID='"+K_ID+"') AND (K.K_ID=I.I_KAIZ_ID)";
	     try {
			
	   	 st = con.createStatement();
		     rs = st.executeQuery(sorguIdBulma);
		     
		     while (rs.next()) {
		    	 //kaiz ve islem tablosundaki �d sini al�p globale at�yorum
		    	System.out.println(rs.getString("K_ID"));
		    	System.out.println(rs.getString("I_ID"));
		    	ka�zId=rs.getString("K_ID");
		    	�slemDeleteId=rs.getString("I_ID");
		    	
		     }

		     
		     
		} catch (Exception e) {
			// TODO: handle exception
		}
	    
	}

	 
	 

	 
	//BURADA PERSONEL�N ORG IS S�N� G�R�S YAPTI�INCDA �EK�YORUM ZATEN
		public static String Kaizen_�statistik(String P_ORG_SEV) {
			  
		    Connection con;
		       String sorguOrgBilg="";
		       Statement st;
		       ResultSet rs;
		       String d�nd�r;
		       int i;
		       String seviye1="",seviye2="",seviye3="",seviye4="";
		       //seveiyelerine g�re at�yorum

		       i=0;
		   
		   	if(P_ORG_SEV.equals("1") || P_ORG_SEV.equals("2") || P_ORG_SEV.equals("3") || P_ORG_SEV.equals("4") ) {
		   		 
		   		if(P_ORG_SEV.equals("1")) {
		   		seviye1="1"; seviye2="1";	seviye3="1"; seviye4="1";
		   	   	}
		   		else if(P_ORG_SEV.equals("2")) {
		   			seviye1="1"; seviye2="1";	seviye3="2"; seviye4="2";
		   	   		
		   		}
		   	   else if(P_ORG_SEV.equals("3")) {
		   		seviye1="1"; seviye2="1";	seviye3="2"; seviye4="3";	
		       }
		   	   else if(P_ORG_SEV.equals("4")) {
		   		seviye1="1"; seviye2="2";	seviye3="3"; seviye4="4";
			}
		   		//DURUMUNA G�RE B�Y�KTEN K����E SIRALIYORUM
		   	 sorguOrgBilg ="SELECT *  FROM KAIZEN_KAIZEN_TBL K  ,KAIZEN_PERSONEL_TBL P,KAIZEN_ISLEM_TBL I ,KAIZEN_DURUM_TBL D  WHERE\r\n" + 
		   	 		"( (P.P_ORG_ID="+seviye1+")OR (P.P_ORG_ID="+seviye2+")OR (P.P_ORG_ID="+seviye3+")OR (P.P_ORG_ID="+seviye4+")) AND (P.P_SICIL=I.I_SICIL_ID)\r\n" + 
		   	 		"AND (I.I_KAIZ_ID=K.K_ID)  AND(D.D_ID=I.I_DURUM_ID)  ORDER BY P.P_ORG_ID DESC";
				}
		   	
		   	
		   	
		  else 	if(P_ORG_SEV.equals("5") || P_ORG_SEV.equals("6") || P_ORG_SEV.equals("7") || P_ORG_SEV.equals("8") ) {
				 
				if(P_ORG_SEV.equals("5")) {
				seviye1="5"; seviye2="5";	seviye3="5"; seviye4="5";
			   	}
				else if(P_ORG_SEV.equals("6")) {
					seviye1="5"; seviye2="5";	seviye3="6"; seviye4="6";
			   		
				}
			   else if(P_ORG_SEV.equals("7")) {
				seviye1="5"; seviye2="6";	seviye3="7"; seviye4="7";	
		    }
			   else if(P_ORG_SEV.equals("8")) {
				seviye1="5"; seviye2="6";	seviye3="7"; seviye4="8";
			}
				
			 sorguOrgBilg ="SELECT *  FROM KAIZEN_KAIZEN_TBL K  ,KAIZEN_PERSONEL_TBL P,KAIZEN_ISLEM_TBL I,KAIZEN_DURUM_TBL D  WHERE\r\n" + 
			 		"( (P.P_ORG_ID="+seviye1+")OR (P.P_ORG_ID="+seviye2+")OR (P.P_ORG_ID="+seviye3+")OR (P.P_ORG_ID="+seviye4+")) AND (P.P_SICIL=I.I_SICIL_ID)\r\n" + 
			 		"AND (I.I_KAIZ_ID=K.K_ID)  AND(D.D_ID=I.I_DURUM_ID) ORDER BY P.P_ORG_ID DESC";
				}
		       
		   	d�nd�r="<table id='tablo'><tr><th>SICIL</th><th>ADI</th><th>SOYADI</th><th>KAIZEN ID</th><th>KAIZEN ADI</th>"
	         		+ "<th>KAIZEN KONU</th><th>BASVURU TARIHI</th><th>TAMAMLANMA TARIHI</th><th>SON HAREKET TARIHI</th>"
	         		+ "<th>ACIKLAMA</th><th>DURUM ID</th>";
	            
		     
		   	
		try {
			 con = Database_Connection.getConnection_Kaizen_Db();
			 st = con.createStatement();
		    rs = st.executeQuery(sorguOrgBilg);
			 
		   while (rs.next()) {
		  
		       
		          
		     d�nd�r+="<tr> <td �d='P_SICIL="+i+"'>"+rs.getString("P_SICIL")+"</td>"+
	          	     	   "<td �d='P_ADI="+i+"'>"+rs.getString("P_ADI")+"</td>"+
					       "<td �d='P_SOYADI="+i+"'>"+rs.getString("P_SOYADI")+"</td>"+
					       "<td �d='K_ID="+i+"'>"+rs.getString("K_ID")+"</td>"+
					       "<td �d='K_ADI="+i+"'>"+rs.getString("K_ADI")+"</td>"+
					       "<td �d='K_KONU"+i+"'>"+rs.getString("K_KONU")+"</td>"+
					       "<td �d='K_BASV_TARH"+i+"'>"+rs.getString("K_BASV_TARH")+"</td>"+
					       "<td �d='K_TAMAM_TARH"+i+"'>"+rs.getString("K_TAMAM_TARH")+"</td>"+
					       "<td �d='K_SON_HARK_TARH"+i+"'>"+rs.getString("K_SON_HARK_TARH")+"</td>"+
					       "<td �d='K_A�IKLAMA"+i+"'>"+rs.getString("K_A�IKLAMA")+"</td>"+
					       "<td �d='I_DURUM_ID"+i+"'>"+rs.getString("D_ADI")+"</td>"+

					                                                            "</tr>";
			     i++;
		   }
		   d�nd�r+="</table>";
		   

		   System.out.println(d�nd�r); 
		} catch (Exception e) {
			// TODO: handle exception
		}

		return d�nd�r;
			
		}
	 
		
		
		public static int [] �statitikOranYaz(String orgId , String orgSevId) {

		    Connection con=null;
		       String sorguKaizen=null;
		       Statement st=null;
		       ResultSet rs=null;
		     
		 
		 
		   int durum[]=new int [8];
		   	int olu�turulud=0,ilkAmirOnay�=0,m�d�rOnay�=0,kaizenRehberOnay�=0,kaizenM�d�r=0,tamamland�=0,reddedildi=0,kabuledildi=0;
		   	 
		   	
		        
		
		   	 sorguKaizen ="SELECT *  FROM KAIZEN_KAIZEN_TBL K  ,KAIZEN_PERSONEL_TBL P,KAIZEN_ISLEM_TBL I,KAIZEN_DURUM_TBL D,KAIZEN_ORGANIZASYON_TBL O,KAIZEN_O_SEVIYE_TBL S WHERE\r\n" + 
		   	 		"(S.S_ID="+orgSevId+")AND(O.O_SEVIYE_ID=S.S_ID) AND (O.O_ID=P.P_ORG_ID) AND (P.P_SICIL=I.I_SICIL_ID)AND \r\n" + 
		   	 		"(I.I_KAIZ_ID=K.K_ID) AND (I.I_DURUM_ID=D.D_ID) AND (O.O_ID="+orgId+")";

		try {//burada bean sayfalar�na at�yorum
			 con = Database_Connection.getConnection_Kaizen_Db();
			 st = con.createStatement();
		    rs = st.executeQuery(sorguKaizen);
			String test="";
		   while (rs.next()) {
			   
		       
		        test= rs.getString("D_ID");
		        
		        
		        if(test.equals("1")) {
		        	olu�turulud++;
		        }else if(test.equals("2")) {
		        	ilkAmirOnay�++;
		        	
		        }else if(test.equals("3")) {
		        	m�d�rOnay�++;
		        	
		        }else if(test.equals("4")) {
		        	kaizenRehberOnay�++;
		        	
		        }else if(test.equals("5")) {
		        	kaizenM�d�r++;
		        	
		        }else if(test.equals("6")) {
		        	tamamland�++;
		        	
		        }else if(test.equals("7")) {
		        	reddedildi++;
		        	
		        }else if(test.equals("8")) {
		        	kabuledildi++;
		        
		        }
		   }
		   durum [0]=olu�turulud;
		   durum [1]=ilkAmirOnay�;
		   durum [2]=m�d�rOnay�;
		   durum [3]=kaizenRehberOnay�;
		   durum [4]=kaizenM�d�r;
		   durum [5]=tamamland�;
		   durum [6]=reddedildi;
		   durum [7]=kabuledildi;
		   
		    
		} catch (Exception e) {
			// TODO: handle exception
		}
		 return durum;
		}
		
		public static String  IstatistikTablo(String orgId , String orgSevId) {
			
			  Connection con=null;
		       String sorguKaizen=null;
		       Statement st=null;
		       ResultSet rs=null;
		       int i=0;
		    	String d�nd�r;
		    
		
		   	 sorguKaizen ="SELECT *  FROM KAIZEN_KAIZEN_TBL K  ,KAIZEN_PERSONEL_TBL P,KAIZEN_ISLEM_TBL I,KAIZEN_DURUM_TBL D,KAIZEN_ORGANIZASYON_TBL O,KAIZEN_O_SEVIYE_TBL S WHERE\r\n" + 
		   	 		"(S.S_ID="+orgSevId+")AND(O.O_SEVIYE_ID=S.S_ID) AND (O.O_ID=P.P_ORG_ID) AND (P.P_SICIL=I.I_SICIL_ID)AND \r\n" + 
		   	 		"(I.I_KAIZ_ID=K.K_ID) AND (I.I_DURUM_ID=D.D_ID) AND (O.O_ID="+orgId+")";

		   	 
		 	d�nd�r="<table id='kaizenler'><tr><th>Personel Sicil</th><th>Personel Adi</th><th>Personel Soyadi</th>"
	         		+ "<th>Kaizen Id</th><th>Kaizen Adi</th><th>Kaizen Konu</th><th>Kaizen Ba�vuru</th><th>Kaizen Tamamlanma</th>"
	         		+ "<th>Kaizen Son Hareket</th><th>Kaizen Durum</th>";
		   	 
		try {
			 con = Database_Connection.getConnection_Kaizen_Db();
			 st = con.createStatement();
		    rs = st.executeQuery(sorguKaizen);
			
		   while (rs.next()) {
			   
			   
			 	
	              d�nd�r+="<tr> <td �d='"+i+"'>"+rs.getString("P_SICIL")+"</td>"+
						       "<td �d='"+i+"'>"+rs.getString("P_ADI")+"</td>"+
						       "<td �d='"+i+"'>"+rs.getString("P_SOYADI")+"</td>"+
						       "<td �d='"+i+"'>"+rs.getString("K_ID")+"</td>"+
						       "<td �d='"+i+"'>"+rs.getString("K_ADI")+"</td>"+
						       "<td �d='"+i+"'>"+rs.getString("K_KONU")+"</td>"+
						       "<td �d='"+i+"'>"+rs.getString("K_BASV_TARH")+"</td>"+
						       "<td �d='"+i+"'>"+rs.getString("K_TAMAM_TARH")+"</td>"+
						       "<td �d='"+i+"'>"+rs.getString("K_SON_HARK_TARH")+"</td>"+
						       "<td �d='"+i+"'>"+rs.getString("D_ADI")+"</td>"+
						      
						                                                            "</tr>";
	                 i++;
		       
		      
		   }
		 
		   d�nd�r+="</table>";
		    
		} catch (Exception e) {
			// TODO: handle exception
		}
		return d�nd�r;
	
		}
		
}




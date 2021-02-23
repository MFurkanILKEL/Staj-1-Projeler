package Servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import MenagerFiles.Kaizen_Manager;
import MenagerFiles.PersonelBilgi;

@WebServlet(urlPatterns = "/Servlet", name = "Servlet")
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Servlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// burada sayfaya y�nledirme yapmak i�in belirledi�imiz bir uzant� ismi ile
		// ba�lan�yoruz
		String islem = request.getParameter("islem");
		PrintWriter pw = response.getWriter();

		if (islem.equals("Giris")) {
			String[] dizi = new String[2];

			String kontrol = request.getParameter("sicil");
			dizi = PersonelBilgi.PersonelSorgu(kontrol);

			System.out.println(dizi[0]);
			System.out.println("org" + dizi[1]);
			String sonuc = dizi[0];
			String OrgId = dizi[1];

			request.getSession().setAttribute("sicil", kontrol);
			request.getSession().setAttribute("OrgId", OrgId);
			request.getSession().setAttribute("seviye", sonuc);

			// burada session ile veriyi tutuyorum
			// sicili-org-seviyesini

			pw.print(sonuc);// burada sayfaya success oldu�unda veriyi g�nderiyorum

		}

		else if (islem.equals("Kaizenler")) {

			String sicil = request.getParameter("sicil");
			String tablo = Kaizen_Manager.Kaizen_Personel(sicil);
			System.out.println("sicili geliyor=" + sicil);

			pw.write(tablo);

		}

		else if (islem.equals("Unitemde")) {

			String OrgId = request.getParameter("OrgId");
			String tablo = Kaizen_Manager.Kaizen_Organizasyon(OrgId);
			System.out.println(OrgId);
			pw.write(tablo);

		}

		else if (islem.equals("Istatistik")) {
			System.out.println("�statistik sayfas�na hosgeldiniz");
			String org = request.getParameter("OrgId");
			System.out.println(org);
			String g�nder = Kaizen_Manager.Kaizen_�statistik(org);
			System.out.println(g�nder);
			pw.write(g�nder);
		}

		else if (islem.equals("KaizenEkle")) {
			System.out.println("Kaizen ekle");
			String sicil = request.getParameter("sicil");
			String Ad� = request.getParameter("KaizenAd");
			String Konu = request.getParameter("KaizenKonu");
			String OrgId = request.getParameter("KaizenOrg");
			String A��klama = request.getParameter("KaizenA��klama");

			
			Kaizen_Manager.Ka�zen_Insert(sicil, Ad�, Konu, "1", OrgId, A��klama);

		}

		// buraya se�ilen kaizen �d sini almak i�in kullan�yorum
		// ara kay�t b�l�m� gibi
		else if (islem.equals("KaizenDetay")) {

			System.out.println("Kaizen detay");
			String secim = request.getParameter("KaizenId");
			String sicil = request.getParameter("sicil");
			System.out.println("secimi" + secim);
			System.out.println("sicili" + sicil);
			request.getSession().setAttribute("secim", secim);

		} else if (islem.equals("Kaizenislem")) {
			String detay;
			String sicil = request.getParameter("sicil");
			String secim = request.getParameter("secim");
			detay = Kaizen_Manager.Ka�zen_Detay(sicil, secim);
			pw.write(detay);
		}

		else if (islem.equals("KaizenYazGetir")) {
			String g�ncelle = request.getParameter("secim");
			System.out.println(g�ncelle);
			request.getSession().setAttribute("yaz", g�ncelle);
			// baska sayfasa kullanablmek i�in session ile tutuyorum
			// burada de�i�ecek metinleri getirmek i�in sayfa a��ld���nda �a��raca��m
		}

		else if (islem.equals("KaizenYaz")) {
			System.out.println("kaizen g�ncele �al�t�");
			String Ad� = request.getParameter("KaizenAd");
			String Konu = request.getParameter("KaizenKonu");
			String OrgId = request.getParameter("KaizenOrg");
			String Ac�klama = request.getParameter("KaizenA��klama");
			String yaz = request.getParameter("yaz");
			String sicil = request.getParameter("sicil");
			System.out.println("g�ncellede" + yaz);
			System.out.println("g�ncellede" + sicil);
			Kaizen_Manager.Ka�zenUpdate(Ad�, Konu, OrgId, yaz, Ac�klama);
			// kaizen �d sini ("3") �nceki sayfaya ba�larken girecek
		}

		else if (islem.equals("KaizenDelete")) {
			System.out.println("silme sayfas�");
			String silinecek = request.getParameter("secim");
			System.out.println(silinecek);
			Kaizen_Manager.Ka�zen_Delete(silinecek);
		}

		// her buton i�in (mevkisi) farkl� de�erleri tutu� sessionlara atayarak
		// yani sayfada �a��r�yorum
		else if (islem.equals("Buton")) {

			System.out.println("bton");
			String OrgId = request.getParameter("org");
			String seviye = request.getParameter("sev");
			System.out.println(seviye);
			System.out.println(OrgId);
			int dizi[] = new int[8];
			dizi = Kaizen_Manager.�statitikOranYaz(OrgId, seviye);
			request.getSession().setAttribute("olustu", dizi[0]);
			request.getSession().setAttribute("amirOnay", dizi[1]);
			request.getSession().setAttribute("mudurOnay", dizi[2]);
			request.getSession().setAttribute("kaizRehbOnay", dizi[3]);
			request.getSession().setAttribute("kaizMudurOnay", dizi[4]);
			request.getSession().setAttribute("tamamland", dizi[5]);
			request.getSession().setAttribute("reddEdildi", dizi[6]);
			request.getSession().setAttribute("kabulEdildi", dizi[7]);

			request.getSession().setAttribute("organizasSayfa", OrgId);
			request.getSession().setAttribute("seviyeSatfa", seviye);

		}
		// AYNI HATAYI BURADA DA G�NDER�YORUM D�KKAT ET

		else if (islem.equals("tabloAl")) {
			System.out.println("tabloya yaz");
			String OrgId = request.getParameter("organizasSayfa");
			String seviye = request.getParameter("seviyeSatfa");

			System.out.println("org" + OrgId);
			System.out.println("sevi" + seviye);
			// veya direkt jsp sayfas�ndan g�nderirken stajyer ise org �d sine g�re
			// -1organ�zasyon -1 seviye diyerek
			// g�nderirsem �al���r
			String tablo = Kaizen_Manager.IstatistikTablo(OrgId, seviye);
			pw.write(tablo);
		}

	}

}

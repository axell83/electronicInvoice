package sri.factura.firma;


//import es.mityc.firmaJava.libreria.xades.DataToSign;
//import es.mityc.firmaJava.libreria.xades.XAdESSchemas;
//import es.mityc.javasign.EnumFormatoFirma;
//import es.mityc.javasign.xml.refs.InternObjectToSign;
//import es.mityc.javasign.xml.refs.ObjectToSign;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;
 
public class XAdESBESSignature
  extends GenericXMLSignature
{
  private static String nameFile;
  private static String pathFile;
  private String fileToSign;
   
  public XAdESBESSignature(String fileToSign)
  {
    this.fileToSign = fileToSign;
  }
   
  public static void firmar(String xmlPath, String pathSignature, String passSignature, String pathOut, String nameFileOut)
    throws CertificateException, IOException
  {
    XAdESBESSignature signature = new XAdESBESSignature(xmlPath);
    signature.setPassSignature(passSignature);
    signature.setPathSignature(pathSignature);
    pathFile = pathOut;
    nameFile = nameFileOut;
     
    signature.execute();
  }
   
  protected String getSignatureFileName()
  {
    return nameFile;
  }
   
  protected String getPathOut()
  {
    return pathFile;
  }
}
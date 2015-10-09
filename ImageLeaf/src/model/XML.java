/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.File;
import java.io.FileOutputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 *
 * @author Anderson
 */
public class XML {
    
    //funcao que lê um xml e retorna o valor da tag passada como parametro
    //a string de retorno contem todo o texto existentes em tags filhas
    public static String lerXml(String caminho, String tag) {

        try {
            //cria uma fabrica de documentos
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            //cria um documento vazio
            File f = new File(caminho);
            Document doc = builder.parse(f);

            Element elemento = doc.getDocumentElement();
            Element node = (Element) elemento.getElementsByTagName(tag).item(0);
            return node.getTextContent();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String lerXml(File f, String tag) {

        try {
            //cria uma fabrica de documentos
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            //converte o file em doc
            Document doc = builder.parse(f);

            Element elemento = doc.getDocumentElement();
            Element node = (Element) elemento.getElementsByTagName(tag).item(0);
            return node.getTextContent();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //funcao que cria o documento XML
    public static void criarXml(String caminho, String nome) {

        try {
            //cria uma fabrica de documentos
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            //cria um documento vazio
            Document doc = builder.newDocument();

            //criando os elementos do documento
            Element padraoElement = doc.createElement("Padrao");
            Element nomeElement = doc.createElement("nome");

            Element histogramaElement = doc.createElement("histograma");

            Element medioElement = doc.createElement("medio");
            Element desvioElement = doc.createElement("desvio");

            Element mredElement = doc.createElement("red");
            Element mgreenElement = doc.createElement("green");
            Element mblueElement = doc.createElement("blue");

            Element dredElement = doc.createElement("red");
            Element dgreenElement = doc.createElement("green");
            Element dblueElement = doc.createElement("blue");

            //criando texto dos conteudos da tags
            Text nomeText = doc.createTextNode(nome);
            Text mredText = doc.createTextNode("hmR.txt");
            Text mgreenText = doc.createTextNode("hmG.txt");
            Text mblueText = doc.createTextNode("hmB.txt");
            Text dredText = doc.createTextNode("hdpR.txt");
            Text dgreenText = doc.createTextNode("hdpG.txt");
            Text dblueText = doc.createTextNode("hdpB.txt");

            //adicionado os elementos ao documento
            doc.appendChild(padraoElement);
            padraoElement.appendChild(nomeElement);
            padraoElement.appendChild(histogramaElement);

            histogramaElement.appendChild(medioElement);
            histogramaElement.appendChild(desvioElement);

            medioElement.appendChild(mredElement);
            medioElement.appendChild(mgreenElement);
            medioElement.appendChild(mblueElement);

            desvioElement.appendChild(dredElement);
            desvioElement.appendChild(dgreenElement);
            desvioElement.appendChild(dblueElement);

            //atribuindo valores ao elementos
            //o nome
            nomeElement.appendChild(nomeText);
            //o nome dos hitograma dentro do documento
            mredElement.appendChild(mredText);
            mgreenElement.appendChild(mgreenText);
            mblueElement.appendChild(mblueText);
            //
            dredElement.appendChild(dredText);
            dgreenElement.appendChild(dgreenText);
            dblueElement.appendChild(dblueText);

            //Serializando o documento
            FileOutputStream output = new FileOutputStream(caminho + "dados.xml");
            XMLSerializer serializer = new XMLSerializer(output, new OutputFormat(doc, "ISO-8859-1", true));
            //escrevendo os dados
            serializer.serialize(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

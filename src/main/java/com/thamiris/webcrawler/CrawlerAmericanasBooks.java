package com.thamiris.webcrawler;

import com.thamiris.model.Classificacao;
import com.thamiris.model.Produto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CrawlerAmericanasBooks {

    private static String URL_AMAZON_BOOKS = "https://www.americanas.com.br/categoria/livros/informatica";

    public static void main(String[] args) throws IOException {

        Document page = Jsoup.connect(URL_AMAZON_BOOKS).userAgent("Jsoup Scraer").get();
        Elements links = page.select("[href*=/produto/]").select("a[href]");

        System.out.println("PRODUTOS LIDOS: " + links.size());

        List<Produto> products = new ArrayList<>();

        for(Element link : links){

            try {
                Produto produto = new Produto();
                String url = "https://www.americanas.com.br" + link.attr("href");

                Document p = Jsoup.connect(url).userAgent("Jsoup Scraer").get();
                String title =  p.getElementsByTag("script").html().split("\"h1\":")[1].split(",")[0];
                String price = p.getElementsByTag("script").html().split("\"price\":")[2].split(",")[0];
                String peso = (p.getElementsByClass("Tr-sc-1wy23hs-3 cNwYXF").text().contains("Peso"))? p.getElementsByClass("Tr-sc-1wy23hs-3 cNwYXF").text().split("Peso")[1].split(" ")[1] : null;
                String desconto =  p.getElementsByTag("script").html().split("\"discount\":")[1].split("\"rate\":")[1].split(",")[0];

                produto.setUrl(url);
                produto.setTitle(title);
                produto.setPrice(Double.parseDouble(price));
                produto.setPeso(peso);
                produto.setDesconto(Integer.parseInt(desconto));

                Classificacao classificacao = new Classificacao();
                classificacao.setAvaliacao(p.getElementsByClass("summary-stats-percentage-dial-text").text() + " " + p.getElementsByClass("summary-stats-percentage-support").text());
                classificacao.setVotos((!p.getElementsByClass("Average-sc-1fg2071-4 eOvPfe TextUI-sc-12tokcy-0 kVUMyx").text().equals(""))? p.getElementsByClass("Average-sc-1fg2071-4 eOvPfe TextUI-sc-12tokcy-0 kVUMyx").text(): null);
                classificacao.setRating((!p.getElementsByClass("Quantity-sc-1fg2071-3 hWIROQ TextUI-sc-12tokcy-0 kVUMyx").text().replace(")","").replace("(","").equals(""))? Integer.valueOf(p.getElementsByClass("Quantity-sc-1fg2071-3 hWIROQ TextUI-sc-12tokcy-0 kVUMyx").text().replace(")","").replace("(","")) : 0);
                produto.setClassificacao(classificacao);

                products.add(produto);

            }catch (Exception e){
                System.out.println(e);
            }
        }

        Produto productMaisBarato = new Produto();
        Produto productMaisAvaliado = new Produto();
        Produto productMaiorDesconto = new Produto();

        Double maisBarato = 0.00;
        Integer maisAvaliado = 0;
        Integer maiorDesconto = 0;

        System.out.println("PRODUTOS CAPTURADOS: " + products.size());
        for(Produto p : products){

            System.out.println(p);

            if(maisBarato == 0.00){
                maisBarato = p.getPrice();
                productMaisBarato= p;
            }
            if(p.getPrice() < maisBarato){
                maisBarato = p.getPrice();
                productMaisBarato= p;
            }

            if(maisAvaliado == 0){
                maisAvaliado = p.getClassificacao().getRating();
                productMaisAvaliado= p;
            }
            if(p.getClassificacao().getRating() > maisAvaliado){
                maisAvaliado = p.getClassificacao().getRating();
                productMaisAvaliado= p;
            }

            if(maiorDesconto == 0){
                maiorDesconto = p.getDesconto();
                productMaiorDesconto = p;
            }
            if(p.getDesconto() > maiorDesconto){
                maiorDesconto = p.getDesconto();
                productMaiorDesconto= p;
            }

        }

        System.out.println();
        System.out.println("PRODUTO MAIS BARATO: " + productMaisBarato.toString());
        System.out.println("PRODUTO MAIS AVALIADO: " + productMaisAvaliado.toString());
        System.out.println("PRODUTO COM MAIOR DESCONTO: " + productMaiorDesconto.toString());

    }
}

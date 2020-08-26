package com.thamiris.model;

public class Produto {

    String url;
    String title;
    Double price;
    String peso;
    Integer desconto;
    Classificacao classificacao;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public void setDesconto(Integer desconto) {
        this.desconto = desconto;
    }

    public void setClassificacao(Classificacao classificacao) {
        this.classificacao = classificacao;
    }

    public Integer getDesconto() {
        return desconto;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public Double getPrice() {
        return price;
    }

    public String getPeso() {
        return peso;
    }

    public Classificacao getClassificacao() {
        return classificacao;
    }

    @Override
    public String toString() {
        return "url: " + this.getUrl() + ", title: " + this.getTitle() + ", price: " + this.getPrice() + ", desconto: " + this.getDesconto() + ", peso: " + this.getPeso() + ", " + this.getClassificacao();
    }
}

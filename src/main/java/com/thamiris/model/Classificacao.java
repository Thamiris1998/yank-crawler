package com.thamiris.model;

public class Classificacao {

    String votos;
    String avaliacao;
    Integer rating;

    public void setVotos(String votos) {
        this.votos = votos;
    }

    public void setAvaliacao(String avaliacao) {
        this.avaliacao = avaliacao;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getRating() {
        return rating;
    }

    public String getVotos() {
        return votos;
    }

    public String getAvaliacao() {
        return avaliacao;
    }

    @Override
    public String toString() {
        return "rating: " + this.getRating() + ", votos: " + this.getVotos() + ", avaliação: " + this.getAvaliacao();
    }

}

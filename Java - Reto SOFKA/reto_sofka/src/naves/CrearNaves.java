package naves;

import javax.swing.*;

public class CrearNaves extends Naves implements calculoParametrosNaves  {

    double pesoNave;
    String destinoCercano;

    @Override
    public String destinoNave(Integer distanciaPlaneta) {

        if(distanciaPlaneta <= 1000){
            destinoCercano = "Saturno";
        } else if (distanciaPlaneta > 1000 && distanciaPlaneta <= 2500){
            destinoCercano = "Pluton";
        } else if (distanciaPlaneta > 2500 && distanciaPlaneta <= 4000){
            destinoCercano = "Jupiter";
        } else if (distanciaPlaneta > 4000 && distanciaPlaneta <= 6000){
            destinoCercano = "Marte";}
        else {
            JOptionPane.showMessageDialog(null,"Parece que quieres ir demasiado lejos!");
            destinoCercano = "No hay destinos";
        }

        return destinoCercano;

    }

    @Override
    public double pesoNave(String tipoNave) {

        if(tipoNave.equals("[Lanzadera]")){
            pesoNave = 3500;
        } else if (tipoNave.equals("[No tripulada]")){
            pesoNave = 2400;
        } else if (tipoNave.equals("[Tripulada]")){
            pesoNave = 2900;
        } else if (tipoNave.equals("[Reconocimiento]")){
            pesoNave = 1800;
        }
        else {
            System.out.println("Tipo de nave no valida");
        }
        return pesoNave;
    }

    @Override
    public Object seleccionarNave(Object nave) {
        return null;
    }

    public Object idNaves(Object tipoNave){
        if (tipoNave.equals("[Lanzadera]")){
            return 1;
        } else if(tipoNave.equals("[No Tripulada]")){
            return 2;
        } else if(tipoNave.equals("[Tripulada]")) {
            return 3;
        } else if(tipoNave.equals("[Reconocimiento]")) {
            return 4;
        }
        return "Nave inválida";
    }

    public String nombreNave(Object idNave) {

        if(idNave.equals(1)) {
            return "[Lanzadera]";

        } else if(idNave.equals(2)) {
            return "[No Tripulada]";

        }else if(idNave.equals(3)) {
            return "[Tripulada]";

        }else if(idNave.equals(4)) {
            return "[Reconocimiento]";
        }else {
            return "[Id no valido]";
        }

    }
}

package br.unicamp.ic.MC322.model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Agrega o estado completo da fábrica (matérias-primas, máquinas e demandas).
 */
public class Planta {

    private List<MateriaPrima> materiasPrimas;
    private List<Maquina> maquinas;
    private List<Demanda> demandas;
    private double orcamento;

    public Planta() {
        this.materiasPrimas = new ArrayList<>();
        this.maquinas = new ArrayList<>();
        this.demandas = new ArrayList<>();
        this.orcamento = 10000.0;
    }

    public void adicionarMateriaPrima(MateriaPrima mp) {
        if (mp != null) this.materiasPrimas.add(mp);
    }

    public void adicionarMaquina(Maquina maquina) {
        if (maquina != null) this.maquinas.add(maquina);
    }

    public void adicionarDemanda(Demanda demanda) {
        if (demanda != null) this.demandas.add(demanda);
    }

    public List<MateriaPrima> getMateriasPrimas() {
        return Collections.unmodifiableList(materiasPrimas);
    }

    public List<Maquina> getMaquinas() {
        return Collections.unmodifiableList(maquinas);
    }

    public List<Demanda> getDemandas() {
        return Collections.unmodifiableList(demandas);
    }

    public double getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(double orcamento) {
        this.orcamento = orcamento;
    }

    /**
     * Substitui atomicamente o estado da planta pelo novo estado recuperado.
     */
    public void substituirEstado(Planta novaPlanta) {
        if (novaPlanta == null) return;
        this.materiasPrimas = new ArrayList<>(novaPlanta.materiasPrimas);
        this.maquinas = new ArrayList<>(novaPlanta.maquinas);
        this.demandas = new ArrayList<>(novaPlanta.demandas);
        this.orcamento = novaPlanta.orcamento;
    }
}
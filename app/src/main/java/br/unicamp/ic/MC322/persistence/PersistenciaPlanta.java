package br.unicamp.ic.MC322.persistence;

import br.unicamp.ic.MC322.exception.PersistenciaException;
import br.unicamp.ic.MC322.model.*;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/**
 * Responsável por salvar, carregar e validar o estado da Planta Industrial em formato XML.
 * 
 * @author Laura Póvoas
 */
public class PersistenciaPlanta {

    private final String caminhoPadrao;

    public PersistenciaPlanta(String caminhoPadrao) {
        this.caminhoPadrao = caminhoPadrao;
    }

    public PersistenciaPlanta() {
        this("planta_estado.xml");
    }

    /**
     * Registra em um arquivo XML os dados relevantes da planta.
     */
    public void salvar(Planta planta) {
        salvar(planta, this.caminhoPadrao);
    }

    public void salvar(Planta planta, String caminhoArquivo) {
        if (planta == null) {
            throw new PersistenciaException("Impossível salvar uma planta com referência nula.");
        }

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            // Elemento Raiz
            Element rootElement = doc.createElement("planta");
            doc.appendChild(rootElement);

            // Orcamento
            Element orcamentoElem = doc.createElement("orcamento");
            orcamentoElem.setTextContent(String.valueOf(planta.getOrcamento()));
            rootElement.appendChild(orcamentoElem);

            // 1. Estoque de Matérias-Primas
            Element materiasPrimasElem = doc.createElement("materiasPrimas");
            rootElement.appendChild(materiasPrimasElem);
            for (MateriaPrima mp : planta.getMateriasPrimas()) {
                Element mpElem = doc.createElement("materiaPrima");
                mpElem.setAttribute("id", String.valueOf(mp.getId()));

                Element nome = doc.createElement("nome");
                nome.setTextContent(mp.getNome());
                mpElem.appendChild(nome);

                Element qtd = doc.createElement("quantidade");
                qtd.setTextContent(String.valueOf(mp.getQuantidade()));
                mpElem.appendChild(qtd);

                Element unidade = doc.createElement("unidade");
                unidade.setTextContent(mp.getUnidade());
                mpElem.appendChild(unidade);

                Element custo = doc.createElement("custoPorLote");
                custo.setTextContent(String.valueOf(mp.getCustoPorLote()));
                mpElem.appendChild(custo);

                materiasPrimasElem.appendChild(mpElem);
            }

            // 2. Máquinas e Subcomponentes (Composição)
            Element maquinasElem = doc.createElement("maquinas");
            rootElement.appendChild(maquinasElem);
            for (Maquina m : planta.getMaquinas()) {
                Element maqElem = doc.createElement("maquina");
                maqElem.setAttribute("classe", m.getClass().getName());

                Element nome = doc.createElement("nome");
                nome.setTextContent(m.getNome());
                maqElem.appendChild(nome);

                Element ligada = doc.createElement("ligada");
                ligada.setTextContent(String.valueOf(m.estaLigada()));
                maqElem.appendChild(ligada);

                Element cap = doc.createElement("capacidadeMaxima");
                cap.setTextContent(String.valueOf(m.getCapacidadeMaxima()));
                maqElem.appendChild(cap);

                Element prob = doc.createElement("probabilidadeFalha");
                prob.setTextContent(String.valueOf(m.getProbabilidadeFalha()));
                maqElem.appendChild(prob);

                Element custo = doc.createElement("custoOperacao");
                custo.setTextContent(String.valueOf(m.getCustoOperacao()));
                maqElem.appendChild(custo);

                Element saude = doc.createElement("saude");
                saude.setTextContent(String.valueOf(m.getSaude()));
                maqElem.appendChild(saude);

                // Detalhes da composição se for MáquinaDeProcessamento
                if (m instanceof MaquinaDeProcessamento) {
                    MaquinaDeProcessamento mp = (MaquinaDeProcessamento) m;
                    Element compElem = doc.createElement("componentes");

                    // Motor
                    Motor motor = mp.getMotor();
                    Element motorElem = doc.createElement("motor");
                    motorElem.setAttribute("potencia", String.valueOf(motor.getPotencia()));
                    motorElem.setAttribute("saude", String.valueOf(motor.getSaude()));
                    motorElem.setAttribute("emFuncionamento", String.valueOf(motor.isEmFuncionamento()));
                    compElem.appendChild(motorElem);

                    // Bico Injetor
                    BicoInjetor bico = mp.getBicoInjetor();
                    Element bicoElem = doc.createElement("bicoInjetor");
                    bicoElem.setAttribute("vazao", String.valueOf(bico.getVazao()));
                    bicoElem.setAttribute("saude", String.valueOf(bico.getSaude()));
                    bicoElem.setAttribute("entupido", String.valueOf(bico.isEntupido()));
                    compElem.appendChild(bicoElem);

                    // Braços Batedores
                    Element bracosElem = doc.createElement("bracosBatedores");
                    for (BracoBatedor braco : mp.getBracosBatedores()) {
                        Element bracoElem = doc.createElement("bracoBatedor");
                        bracoElem.setAttribute("idBraco", String.valueOf(braco.getIdBraco()));
                        bracoElem.setAttribute("velocidadeRotacao", String.valueOf(braco.getVelocidadeRotacao()));
                        bracoElem.setAttribute("saude", String.valueOf(braco.getSaude()));
                        bracosElem.appendChild(bracoElem);
                    }
                    compElem.appendChild(bracosElem);
                    maqElem.appendChild(compElem);
                }

                maquinasElem.appendChild(maqElem);
            }

            // 3. Demandas de Produção e Estados (StatusDemanda)
            Element demandasElem = doc.createElement("demandas");
            rootElement.appendChild(demandasElem);
            for (Demanda d : planta.getDemandas()) {
                Element demElem = doc.createElement("demanda");

                Element prodElem = doc.createElement("produto");
                // Correção 1: d.getTipoProduto().getId() convertido para String e inserção da classe/lote do produto
                prodElem.setAttribute("classe", d.getTipoProduto().getClass().getName());
                prodElem.setAttribute("id", String.valueOf(d.getTipoProduto().getId()));
                prodElem.setAttribute("nome", d.getTipoProduto().getNome());
                prodElem.setAttribute("lote", d.getTipoProduto().getLote());
                prodElem.setAttribute("mpNecessaria", String.valueOf(d.getTipoProduto().getQuantidadeMateriaPrimaNecessaria()));
                demElem.appendChild(prodElem);

                Element qtdElem = doc.createElement("quantidadeProdutos");
                qtdElem.setTextContent(String.valueOf(d.getQuantidadeProdutos()));
                demElem.appendChild(qtdElem);

                Element statusElem = doc.createElement("status");
                statusElem.setTextContent(d.getStatus().name());
                demElem.appendChild(statusElem);

                demandasElem.appendChild(demElem);
            }

            // Escreve no arquivo de destino
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(caminhoArquivo));
            transformer.transform(source, result);

        } catch (Exception e) {
            throw new PersistenciaException("Falha ao salvar o estado da planta em XML.", e);
        }
    }

    /**
     * Lê um arquivo XML, valida seu conteúdo e constrói um novo objeto Planta.
     */
    public Planta carregar() {
        return carregar(this.caminhoPadrao);
    }

    public Planta carregar(String caminhoArquivo) {
        File arq = new File(caminhoArquivo);

        // Tratamento 1: Arquivo inexistente
        if (!arq.exists()) {
            throw new PersistenciaException("Arquivo de persistência não encontrado: " + caminhoArquivo);
        }

        // Tratamento 2: Arquivo vazio
        if (arq.length() == 0) {
            throw new PersistenciaException("O arquivo de persistência está totalmente vazio: " + caminhoArquivo);
        }

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(arq);
            doc.getDocumentElement().normalize();

            if (!"planta".equals(doc.getDocumentElement().getNodeName())) {
                throw new PersistenciaException("Formato XML inválido: nó raiz deve ser <planta>.");
            }

            Planta plantaRecuperada = new Planta();

            // Orçamento
            NodeList nOrcamento = doc.getElementsByTagName("orcamento");
            if (nOrcamento.getLength() > 0) {
                double orc = Double.parseDouble(nOrcamento.item(0).getTextContent());
                if (orc < 0) throw new PersistenciaException("Validação falhou: orçamento negativo.");
                plantaRecuperada.setOrcamento(orc);
            }

            // 1. Carregar Matérias-Primas
            NodeList nListMP = doc.getElementsByTagName("materiaPrima");
            for (int i = 0; i < nListMP.getLength(); i++) {
                Node nNode = nListMP.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) nNode;
                    // Correção 2: Long.parseLong ao invés de Long.parseDouble
                    long id = Long.parseLong(elem.getAttribute("id"));
                    String nome = elem.getElementsByTagName("nome").item(0).getTextContent();
                    double qtd = Double.parseDouble(elem.getElementsByTagName("quantidade").item(0).getTextContent());
                    String unidade = elem.getElementsByTagName("unidade").item(0).getTextContent();
                    double custo = Double.parseDouble(elem.getElementsByTagName("custoPorLote").item(0).getTextContent());

                    // Validação de Regra de Negócio
                    if (qtd < 0 || custo < 0) {
                        throw new PersistenciaException("Validação falhou: valores de matéria-prima não podem ser negativos.");
                    }

                    plantaRecuperada.adicionarMateriaPrima(new MateriaPrima(id, nome, qtd, unidade, custo));
                }
            }

            // 2. Carregar Máquinas
            NodeList nListMaq = doc.getElementsByTagName("maquina");
            for (int i = 0; i < nListMaq.getLength(); i++) {
                Node nNode = nListMaq.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) nNode;
                    String classeName = elem.getAttribute("classe");
                    String nome = elem.getElementsByTagName("nome").item(0).getTextContent();
                    boolean ligada = Boolean.parseBoolean(elem.getElementsByTagName("ligada").item(0).getTextContent());
                    double cap = Double.parseDouble(elem.getElementsByTagName("capacidadeMaxima").item(0).getTextContent());
                    float prob = Float.parseFloat(elem.getElementsByTagName("probabilidadeFalha").item(0).getTextContent());
                    float custo = Float.parseFloat(elem.getElementsByTagName("custoOperacao").item(0).getTextContent());
                    int saude = Integer.parseInt(elem.getElementsByTagName("saude").item(0).getTextContent());

                    Maquina maquina;
                    if (classeName.contains("MaquinaDeProcessamento")) {
                        NodeList compList = elem.getElementsByTagName("bracoBatedor");
                        int qtdBracos = Math.max(1, compList.getLength());
                        MaquinaDeProcessamento mp = new MaquinaDeProcessamento(nome, custo, cap, qtdBracos);

                        // Restaura estado dos subcomponentes compostos
                        NodeList nMotor = elem.getElementsByTagName("motor");
                        if (nMotor.getLength() > 0) {
                            Element eMotor = (Element) nMotor.item(0);
                            mp.getMotor().setSaude(Integer.parseInt(eMotor.getAttribute("saude")));
                            mp.getMotor().setEmFuncionamento(Boolean.parseBoolean(eMotor.getAttribute("emFuncionamento")));
                        }

                        NodeList nBico = elem.getElementsByTagName("bicoInjetor");
                        if (nBico.getLength() > 0) {
                            Element eBico = (Element) nBico.item(0);
                            mp.getBicoInjetor().setSaude(Integer.parseInt(eBico.getAttribute("saude")));
                            mp.getBicoInjetor().setEntupido(Boolean.parseBoolean(eBico.getAttribute("entupido")));
                        }

                        for (int j = 0; j < compList.getLength(); j++) {
                            Element eBraco = (Element) compList.item(j);
                            int saudeBraco = Integer.parseInt(eBraco.getAttribute("saude"));
                            if (j < mp.getBracosBatedores().size()) {
                                mp.getBracosBatedores().get(j).setSaude(saudeBraco);
                            }
                        }
                        maquina = mp;
                    } else {
                        maquina = new MaquinaEmbaladora(nome, custo, cap);
                    }

                    if (ligada) maquina.ligar(); else maquina.desligar();
                    maquina.setProbabilidadeFalha(prob);
                    maquina.setSaude(saude);
                    plantaRecuperada.adicionarMaquina(maquina);
                }
            }

            // 3. Carregar Demandas e Estados
            NodeList nListDem = doc.getElementsByTagName("demanda");
            for (int i = 0; i < nListDem.getLength(); i++) {
                Node nNode = nListDem.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) nNode;
                    Element pElem = (Element) elem.getElementsByTagName("produto").item(0);

                    // Correção 3: Instanciação das subclasses concretas de Produto
                    int pId = Integer.parseInt(pElem.getAttribute("id"));
                    String pNome = pElem.getAttribute("nome");
                    String pLote = pElem.hasAttribute("lote") ? pElem.getAttribute("lote") : "LOTE-01";
                    String pClasse = pElem.hasAttribute("classe") ? pElem.getAttribute("classe") : "";

                    Produto prod;
                    if (pClasse.contains("ChocolatePremium")) {
                        prod = new ChocolatePremium(pId, pNome, pLote);
                    } else if (pClasse.contains("ChocolateSeboso")) {
                        prod = new ChocolateSeboso(pId, pNome, pLote);
                    } else {
                        prod = new ChocolateMeiaBoca(pId, pNome, pLote);
                    }

                    long qtdProd = Long.parseLong(elem.getElementsByTagName("quantidadeProdutos").item(0).getTextContent());
                    String statusStr = elem.getElementsByTagName("status").item(0).getTextContent();

                    Demanda dem = new Demanda(prod, qtdProd);
                    dem.setStatus(StatusDemanda.valueOf(statusStr));
                    plantaRecuperada.adicionarDemanda(dem);
                }
            }

            return plantaRecuperada;

        } catch (PersistenciaException pe) {
            throw pe;
        } catch (Exception e) {
            throw new PersistenciaException("Falha ao analisar o arquivo XML (formato corrompido ou inválido).", e);
        }
    }
}
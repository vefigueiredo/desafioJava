package com.desafioJava.core.service;

import com.desafioJava.core.interfaces.FormatacaoNfiscal;
import com.desafioJava.core.interfaces.NFiscaisDao;
import com.desafioJava.core.models.NotaFiscal;
import com.desafioJava.core.models.NotaFiscalFormat;
import com.desafioJava.core.models.Produto;
import com.desafioJava.core.models.ProdutoNFFormat;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.ArrayList;
import java.util.List;

@Component(immediate = true,service = FormatacaoNfiscal.class)
public class FormatacaoNfiscalImpl implements FormatacaoNfiscal{
    @Reference
    private NFiscaisDao nFiscalDao;
    public List<NotaFiscalFormat> formatNF(long numero){
        int qtn = 1;
        double valor=0;
        List<NotaFiscal> listNF = nFiscalDao.listaNF(numero);
        List<NotaFiscalFormat> listaNF = new ArrayList<>();
        List<ProdutoNFFormat>listProdForm = new ArrayList<>();
        for (Produto prod:listNF.get(0).getListaProdutos()) {
            valor+= prod.getPreco();
            if(listProdForm.size()>0){
                for (int j = 0; j <listProdForm.size() ; j++) {
                    if(listProdForm.get(j).getId()==prod.getId()){
                        qtn= listProdForm.get(j).getQtn()+1;
                        listProdForm.get(j).setQtn(qtn);
                        break;
                    }else {
                        qtn=1;
                        listProdForm.add(new ProdutoNFFormat(prod.getId(), prod.getNome(),prod.getPreco(),qtn));
                        break;
                    }
                }
            }else{
                listProdForm.add(new ProdutoNFFormat(prod.getId(), prod.getNome(),prod.getPreco(),qtn));
                qtn=1;
            }
        }

        long numNF = listNF.get(0).getNumero();
        int idClient= listNF.get(0).getIdCliente();

        listaNF.add(new NotaFiscalFormat(numNF,idClient,valor,listProdForm));
        return listaNF;
    }


}

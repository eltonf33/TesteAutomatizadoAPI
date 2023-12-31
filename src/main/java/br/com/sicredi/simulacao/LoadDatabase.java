package br.com.sicredi.simulacao;

import br.com.sicredi.simulacao.entity.Restricao;
import br.com.sicredi.simulacao.entity.Simulacao;
import br.com.sicredi.simulacao.entity.TipoRestricao;
import br.com.sicredi.simulacao.repository.RestricaoRepository;
import br.com.sicredi.simulacao.repository.SimulacaoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabaseRestricao(RestricaoRepository restricaoRepository) {
        return args -> {
            restricaoRepository.save(new Restricao("97093236014", TipoRestricao.BLOQUEIO_JURICIAL.value()));
            restricaoRepository.save(new Restricao("60094146012", TipoRestricao.CARTAO_CREDITO.value()));
            restricaoRepository.save(new Restricao("84809766080", TipoRestricao.BANCARIA.value()));
            restricaoRepository.save(new Restricao("62648716050", TipoRestricao.SPC.value()));
            restricaoRepository.save(new Restricao("26276298085", TipoRestricao.SPC.value()));
            restricaoRepository.save(new Restricao("01317496094", TipoRestricao.CARTAO_CREDITO.value()));
            restricaoRepository.save(new Restricao("55856777050", TipoRestricao.BANCARIA.value()));
            restricaoRepository.save(new Restricao("19626829001", TipoRestricao.BLOQUEIO_JURICIAL.value()));
            restricaoRepository.save(new Restricao("24094592008", TipoRestricao.BANCARIA.value()));
            restricaoRepository.save(new Restricao("58063164083", TipoRestricao.BANCARIA.value()));
        };
    }

    @Bean
    CommandLineRunner initDatabaseSimulacao(SimulacaoRepository simulacaoRepository) {
        //Simulacao.Builder().cpf("").build();

        Simulacao simulacao = new Simulacao();
        simulacao.setCpf("66414919004");
        simulacao.setNome("Fulano");
        simulacao.setEmail("fulano@gmail.com");
        simulacao.setValor(new BigDecimal(11000));
        simulacao.setParcelas(3);
        simulacao.setSeguro(true);

        Simulacao simulacao2 = new Simulacao();
        simulacao2.setCpf("17822386034");
        simulacao2.setNome("Deltrano");
        simulacao2.setEmail("deltrano@gmail.com");
        simulacao2.setValor(new BigDecimal(20000));
        simulacao2.setParcelas(5);
        simulacao2.setSeguro(false);


        return  args -> {
            simulacaoRepository.save(simulacao);
            simulacaoRepository.save(simulacao2);
        };

//        return  args -> {
//            simulacaoRepository.save(Simulacao.builder().cpf("66414919004").nome("Fulano").email("fulano@gmail.com").valor(new BigDecimal(11000)).parcelas(3).seguro(true).build());
//            simulacaoRepository.save(Simulacao.builder().cpf("17822386034").nome("Deltrano").email("deltrano@gmail.com").valor(new BigDecimal(20000)).parcelas(5).seguro(false).build());
//        };
    }
}
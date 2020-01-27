package pl.icwt.config;

import org.modelmapper.ModelMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class MapperConfig {

    @Produces
    public ModelMapper modelMapper() {

        return new ModelMapper();
    }
}

package ar.edu.unju.fi.arquitecturas.tp2.mapper;

import ar.edu.unju.fi.arquitecturas.tp2.dto.ClienteRequestDto;
import ar.edu.unju.fi.arquitecturas.tp2.dto.ClienteResponseDto;
import ar.edu.unju.fi.arquitecturas.tp2.model.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    Cliente toEntity(ClienteRequestDto dto);
    @Mapping(target = "estado", expression = "java(entity.getEstado().name())")
    @Mapping(target = "mensaje", source = "mensajePersonalizado")
    ClienteResponseDto toDto(Cliente entity, String mensajePersonalizado);
}

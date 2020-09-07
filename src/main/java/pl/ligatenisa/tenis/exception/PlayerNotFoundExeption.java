package pl.ligatenisa.tenis.exception;

public class PlayerNotFoundExeption extends RuntimeException {

    public PlayerNotFoundExeption(Long id){
        super("Zawodnik o id " +id + " nie istnieje");
    }

}

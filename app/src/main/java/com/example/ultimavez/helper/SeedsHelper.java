package com.example.ultimavez.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.ultimavez.MyCustomApplication;
import com.example.ultimavez.R;
import com.example.ultimavez.model.domain.Cupom;
import com.example.ultimavez.model.domain.Product;
import com.example.ultimavez.model.domain.User;
import com.example.ultimavez.model.enums.CategoryEnum;
import com.example.ultimavez.model.enums.UserEnum;
import com.example.ultimavez.persistence.CupomPersistence;
import com.example.ultimavez.persistence.ProductPersistence;
import com.example.ultimavez.persistence.UserPersistence;

import java.io.ByteArrayOutputStream;

public class SeedsHelper {

    private ProductPersistence product;
    private UserPersistence user;
    private CupomPersistence cupom;
    private Context context;

    public SeedsHelper(Context context) {
        this.product = MyCustomApplication.getProductPersistence();
        this.user = MyCustomApplication.getUserPersistence();
        this.cupom = MyCustomApplication.getCupomPersistence();
        this.context = context;
    }

    public void createSeeds() {
        if (!user.existsByEmail("lucas.pereira@gmail.com") && !product.existsByName("Limão e Coco")) {
            saveUsers();
            saveProducts();
            saveCupons();
        }
    }

    private void saveUsers() {
        user.saveUser(new User(UserEnum.SELLER, "aline.barbosa@gmail.com", "0cef1fb1-0f60-3290-a8a7-1f58e54ed07b", "Alive Batista", "122.323.765-55", "(11)94321-5678", "Rua Marechal Deodoro, 1287", "09123-345", "São Bernardo do Campo", "0cef1fb1-0f60-3290-a8a7-1f58e54ed07b"));
        user.saveUser(new User(UserEnum.SELLER, "jose.matos@gmail.com", "0cef1fb1-0f60-3290-a8a7-1f58e54ed07b", "José Garcia Matos", "987.492.312-90", "(11)91234-5098", "Rua dos Feltrins, 21", "03424-096", "Guarulhos", "0cef1fb1-0f60-3290-a8a7-1f58e54ed07b"));
        user.saveUser(new User(UserEnum.CUSTOMER, "luciana.alves@hotmail.com", "0cef1fb1-0f60-3290-a8a7-1f58e54ed07b", "Luciana Alves da Silva", "434.323.656.87", "(11)98765-4321", "Avenida Paulista, 2000", "09876-543", "São Paulo", "0cef1fb1-0f60-3290-a8a7-1f58e54ed07b"));
        user.saveUser(new User(UserEnum.CUSTOMER, "lucas.pereira@gmail.com", "0cef1fb1-0f60-3290-a8a7-1f58e54ed07b", "Lucas Moraes Pereira", "412.893.818-92", "(11)97654-7890", "Avenida Brasil, 345", "09354-221", "Goiânia", "0cef1fb1-0f60-3290-a8a7-1f58e54ed07b"));
    }

    private void saveProducts() {
        // SELLER 1
        product.save(new Product("Floresta Negra", CategoryEnum.PREMIUM, "Chocolate amargo com recheio de cereja e licor", 7.5, image()), 1);
        product.save(new Product("Ninhorango", CategoryEnum.PREMIUM, "Ninho com recheio morango", 6.8, image()), 1);
        product.save(new Product("Churros", CategoryEnum.ESPECIAIS, "Doce de leite com canela", 8.2, image()), 1);
        product.save(new Product("Oreo", CategoryEnum.ESPECIAIS, "Oreo com massa de baunilha", 5.5, image()), 1);
        product.save(new Product("Nutella", CategoryEnum.GOURMET, "Massa branca e Nutella", 10.0, image()), 1);
        product.save(new Product("Laranja", CategoryEnum.GOURMET, "Ganache meio amargo e licor de laranja", 8.5, image()), 1);
        product.save(new Product("Sabor de Verão", CategoryEnum.VEGANO, "Massa vegana com frutas da esteção", 9.3, image()), 1);

        // SELLER 2
        product.save(new Product("Café", CategoryEnum.PREMIUM, "Massa preta com licor de café", 5.2, image()), 2);
        product.save(new Product("Tradicional", CategoryEnum.PREMIUM, "Massa branca com creme", 4.0, image()), 2);
        product.save(new Product("Pistache", CategoryEnum.ESPECIAIS, "Pistache italiano e merengue", 8.2, image()), 2);
        product.save(new Product("Frutas Vermelhas", CategoryEnum.ESPECIAIS, "Massa branca com geleia de frutas vermelhas", 7.6, image()), 2);
        product.save(new Product("Tiramisu", CategoryEnum.GOURMET, "Biscoito Champagne com café e cream cheese", 10.5, image()), 2);
        product.save(new Product("Panna Cotta", CategoryEnum.GOURMET, "Massa branca com panna cotta de amora", 9.8, image()), 2);
        product.save(new Product("Baunilha", CategoryEnum.VEGANO, "Tradicional com recheio de baunilha vegana", 6.9, image()), 2);

        // Categoria PREMIUM
        product.save(new Product("Trufa de Maracujá", CategoryEnum.PREMIUM, "Cobertura de chocolate branco com recheio de trufa de maracujá", 8.0, image()), 1);
        product.save(new Product("Romeu e Julieta", CategoryEnum.PREMIUM, "Goiabada com queijo na massa de baunilha", 7.2, image()), 2);
        product.save(new Product("Crocante de Amêndoas", CategoryEnum.PREMIUM, "Cobertura de chocolate ao leite com amêndoas crocantes", 9.5, image()), 1);
        product.save(new Product("Cheesecake de Frutas Vermelhas", CategoryEnum.PREMIUM, "Massa de cheesecake com calda de frutas vermelhas", 9.5, image()), 2);
        product.save(new Product("Amêndoas e Caramelo Salgado", CategoryEnum.PREMIUM, "Cobertura de caramelo salgado com amêndoas caramelizadas", 10.8, image()), 1);

        // Categoria ESPECIAIS
        product.save(new Product("Amendoim Caramelizado", CategoryEnum.ESPECIAIS, "Cobertura de caramelo com amendoim", 6.9, image()), 2);
        product.save(new Product("Maracujá com Coco", CategoryEnum.ESPECIAIS, "Creme de maracujá com coco ralado", 8.3, image()), 1);
        product.save(new Product("Mousse de Morango", CategoryEnum.ESPECIAIS, "Massa de morango recheada com mousse de morango", 7.6, image()), 2);
        product.save(new Product("Cereja e Pistache", CategoryEnum.ESPECIAIS, "Creme de cereja com pistaches picados", 9.2, image()), 1);
        product.save(new Product("Cappuccino", CategoryEnum.ESPECIAIS, "Massa de café com recheio de creme de cappuccino", 8.9, image()), 2);

        // Categoria GOURMET
        product.save(new Product("Limão Siciliano", CategoryEnum.GOURMET, "Creme de limão siciliano com raspas de limão", 8.0, image()), 1);
        product.save(new Product("Framboesa e Chocolate Branco", CategoryEnum.GOURMET, "Creme de framboesa com pedaços de chocolate branco", 10.2, image()), 2);
        product.save(new Product("Chocolate 70% Cacau", CategoryEnum.GOURMET, "Massa de chocolate intenso com recheio de chocolate 70% cacau", 11.5, image()), 1);
        product.save(new Product("Hortelã e Chocolate", CategoryEnum.GOURMET, "Creme de hortelã com pedaços de chocolate meio amargo", 9.7, image()), 2);
        product.save(new Product("Damasco e Nozes", CategoryEnum.GOURMET, "Creme de damasco com nozes trituradas", 10.4, image()), 1);

        // Categoria VEGANO
        product.save(new Product("Coco Tropical", CategoryEnum.VEGANO, "Massa vegana com coco e abacaxi", 8.4, image()), 2);
        product.save(new Product("Chocolate Vegano", CategoryEnum.VEGANO, "Massa de chocolate vegano com ganache de chocolate amargo", 7.0, image()), 1);
        product.save(new Product("Morango e Banana", CategoryEnum.VEGANO, "Creme de morango com banana na massa vegana", 9.1, image()), 2);
        product.save(new Product("Avelã e Chocolate Amargo", CategoryEnum.VEGANO, "Creme de avelã com chocolate amargo na massa vegana", 9.0, image()), 1);
        product.save(new Product("Limão e Coco", CategoryEnum.VEGANO, "Creme de limão com coco ralado na massa vegana", 8.6, image()), 2);

    }

    private void saveCupons() {
        cupom.saveCupom(new Cupom("QWERTY", true, 10.0));
        cupom.saveCupom(new Cupom("ASDFGHJKL", true, 5.0));
        cupom.saveCupom(new Cupom("POIUYTREWQ", false, 5.0));
    }

    public byte[] image() {
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.generic_cupcake);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}

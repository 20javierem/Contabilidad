package org.moreno;

import org.moreno.models.Category;
import org.moreno.models.Product;
import org.moreno.utilities.Contabilidad;
import org.moreno.utilities.Utilities;
import org.moreno.views.VPrincipal;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        Utilities.tema("genome");
        Contabilidad.initialize();
        Category category=new Category();
        category.setActive(true);
        category.setName("CONSTRUCCIÓN");
        category.save();
        Product product=new Product();
        product.setName("CEMENTO SOL");
        product.setStockActual(0.0);
        product.setActive(true);
        product.setCategory(category);
        product.setUnitMeasure("BOLSAS");
        product.save();
        VPrincipal vPrincipal=new VPrincipal();
        vPrincipal.setVisible(true);
    }
}

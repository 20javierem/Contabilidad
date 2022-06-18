package org.moreno;

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
        VPrincipal vPrincipal=new VPrincipal();
        vPrincipal.setVisible(true);
    }
}

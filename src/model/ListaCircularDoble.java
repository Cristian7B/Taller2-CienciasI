package model;


import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Implementación de una lista enlazada doble y circular genérica.
 * En esta estructura, el último nodo apunta de vuelta al primer nodo (cabeza),
 * y el primer nodo apunta de vuelta al último nodo (anterior).
 * Se utiliza una única referencia externa, {@code ultimo}, que apunta al último nodo,
 * lo que permite acceso O(1) a la cabeza ({@code ultimo.getSiguiente()}) y
 * operaciones O(1) de inserción al inicio y al final.
 * Utiliza la clase {@link NodoDoble}.
 *
 * @param <T> El tipo de elementos almacenados en la lista.
 * @see NodoDoble
 * @author devapps
 * @version 1.2
 */
public class ListaCircularDoble<T> {

    /**
     * Referencia al último nodo de la lista circular.
     * Si la lista está vacía, es {@code null}.
     * Si la lista tiene un solo elemento, {@code ultimo} apunta a ese elemento,
     * y ese elemento apunta a sí mismo tanto en siguiente como anterior.
     * En una lista con más elementos, {@code ultimo.getSiguiente()} devuelve la cabeza
     * y {@code ultimo.getAnterior()} devuelve el penúltimo nodo.
     */
    private NodoDoble<T> ultimo;
    /** Número actual de elementos en la lista. */
    private int tamanno;

    /**
     * Construye una lista enlazada simple circular vacía.
     */
    public ListaCircularDoble() {
        this.ultimo = null;
        this.tamanno = 0;       
    }

    // --- Información Básica y Acceso Interno ---

    /**
     * Devuelve el número de elementos en la lista.
     * @return El tamaño actual de la lista.
     */
    public int getTamanno() {
        return tamanno;
    }

    /**
     * Comprueba si la lista está vacía.
     * @return {@code true} si la lista no tiene elementos, {@code false} en caso contrario.
     */
    public boolean estaVacia() {
        return tamanno == 0;
    }

    /**
     * Obtiene el nodo cabeza de la lista circular (el primer elemento).
     * El acceso es O(1) a través de la referencia {@code ultimo}.
     * @return El {@link NodoDoble} cabeza, o {@code null} si la lista está vacía.
     */
    public NodoDoble<T> getCabeza() {
        // Si no está vacía, la cabeza es el siguiente del último nodo.
        return estaVacia() ? null : this.ultimo.getSiguiente();
    }

    // --- Métodos de Inserción ---

    /**
     * Inserta un elemento al principio de la lista (se convierte en la nueva cabeza).
     * Operación de tiempo constante O(1).
     * @param dato El dato a insertar.
     */
    public void insertarAlInicio(T dato) {
        NodoDoble<T> nuevoNodo = new NodoDoble<>(dato);
        if (estaVacia()) {
            // Primer nodo: es el último y apunta a sí mismo en ambas direcciones
            this.ultimo = nuevoNodo;
            this.ultimo.setSiguiente(this.ultimo);
            this.ultimo.setAnterior(this.ultimo);
        } else {
            NodoDoble<T> cabezaAntigua = this.ultimo.getSiguiente();
            // Nuevo nodo apunta a la antigua cabeza como siguiente
            nuevoNodo.setSiguiente(cabezaAntigua);
            // Nuevo nodo apunta al último como anterior
            nuevoNodo.setAnterior(this.ultimo);
            // La antigua cabeza apunta al nuevo nodo como anterior
            cabezaAntigua.setAnterior(nuevoNodo);
            // El último nodo ahora apunta al nuevo nodo como siguiente
            this.ultimo.setSiguiente(nuevoNodo);
        }
        this.tamanno++;
    }

    /**
     * Inserta un elemento al final de la lista (se convierte en el nuevo {@code ultimo}).
     * Operación de tiempo constante O(1).
     * @param dato El dato a insertar.
     */
    public void insertarAlFinal(T dato) {
        NodoDoble<T> nuevoNodo = new NodoDoble<>(dato);
        if (estaVacia()) {
            // Primer nodo: es el último y apunta a sí mismo en ambas direcciones
            this.ultimo = nuevoNodo;
            this.ultimo.setSiguiente(this.ultimo);
            this.ultimo.setAnterior(this.ultimo);
        } else {
            NodoDoble<T> cabeza = this.ultimo.getSiguiente();
            // Nuevo nodo apunta a la cabeza como siguiente
            nuevoNodo.setSiguiente(cabeza);
            // Nuevo nodo apunta al actual último como anterior
            nuevoNodo.setAnterior(this.ultimo);
            // La cabeza apunta al nuevo nodo como anterior
            cabeza.setAnterior(nuevoNodo);
            // El actual último apunta al nuevo nodo como siguiente
            this.ultimo.setSiguiente(nuevoNodo);
            // El nuevo nodo se convierte en el último
            this.ultimo = nuevoNodo;
        }
        this.tamanno++;
    }

    /**
     * Alias conveniente para {@link #insertarAlFinal(Object)}.
     * @param dato El dato a agregar al final.
     */
    public void agregar(T dato) {
        insertarAlFinal(dato);
    }

    /**
     * Inserta un nuevo elemento {@code datoNuevo} inmediatamente después de la primera
     * ocurrencia del nodo que contiene {@code datoExistente}.
     * Si {@code datoExistente} no se encuentra, la lista no se modifica.
     * La búsqueda es O(n).
     *
     * @param datoExistente El dato del nodo referencia. Se compara usando {@code equals()}.
     * @param datoNuevo El dato a insertar.
     * @return {@code true} si la inserción fue exitosa, {@code false} si {@code datoExistente} no fue encontrado.
     */
    public boolean insertarDespuesDe(T datoExistente, T datoNuevo) {
        NodoDoble<T> nodoExistente = buscarNodo(datoExistente);
        if (nodoExistente == null) {
            return false; // Nodo referencia no encontrado
        }

        NodoDoble<T> nuevoNodo = new NodoDoble<>(datoNuevo);
        NodoDoble<T> nodoSiguiente = nodoExistente.getSiguiente();
        
        // Enlazar el nuevo nodo después del existente
        nuevoNodo.setSiguiente(nodoSiguiente);
        nuevoNodo.setAnterior(nodoExistente);
        nodoExistente.setSiguiente(nuevoNodo);
        nodoSiguiente.setAnterior(nuevoNodo);

        // Si insertamos después del que era el último nodo, el nuevo nodo es el nuevo último
        if (nodoExistente == this.ultimo) {
            this.ultimo = nuevoNodo;
        }
        this.tamanno++;
        return true;
    }

    /**
     * Inserta un nuevo elemento {@code datoNuevo} inmediatamente antes de la primera
     * ocurrencia del nodo que contiene {@code datoExistente}.
     * Si {@code datoExistente} es la cabeza, equivale a {@link #insertarAlInicio(Object)}.
     * Si {@code datoExistente} no se encuentra, la lista no se modifica.
     * La búsqueda es O(n).
     *
     * @param datoExistente El dato del nodo referencia. Se compara usando {@code equals()}.
     * @param datoNuevo El dato a insertar.
     * @return {@code true} si la inserción fue exitosa, {@code false} si {@code datoExistente} no fue encontrado.
     */
    public boolean insertarAntesDe(T datoExistente, T datoNuevo) {
        if (estaVacia()) {
            return false;
        }

        NodoDoble<T> nodoExistente = buscarNodo(datoExistente);
        if (nodoExistente == null) {
            return false; // No se encontró
        }

        NodoDoble<T> nuevoNodo = new NodoDoble<>(datoNuevo);
        NodoDoble<T> nodoAnterior = nodoExistente.getAnterior();
        
        // Enlazar el nuevo nodo antes del existente
        nuevoNodo.setSiguiente(nodoExistente);
        nuevoNodo.setAnterior(nodoAnterior);
        nodoAnterior.setSiguiente(nuevoNodo);
        nodoExistente.setAnterior(nuevoNodo);
        
        this.tamanno++;
        return true;
    }

    // --- Métodos de Eliminación ---

    /**
     * Elimina y devuelve el elemento al principio de la lista (la cabeza).
     * Operación de tiempo constante O(1).
     *
     * @return El dato del elemento eliminado.
     * @throws NoSuchElementException si la lista está vacía.
     */
    public T eliminarAlInicio() {
        if (estaVacia()) {
            throw new NoSuchElementException("No se puede eliminar de una lista circular vacía.");
        }

        NodoDoble<T> cabeza = getCabeza(); // El nodo a eliminar
        T datoEliminado = cabeza.getDato();

        if (this.ultimo == cabeza) { // Si solo había un elemento
            this.ultimo = null; // La lista queda vacía
        } else {
            NodoDoble<T> nuevaCabeza = cabeza.getSiguiente();
            // El último nodo ahora apunta a la nueva cabeza
            this.ultimo.setSiguiente(nuevaCabeza);
            // La nueva cabeza apunta al último como anterior
            nuevaCabeza.setAnterior(this.ultimo);
        }
        this.tamanno--;
        return datoEliminado;
    }

    /**
     * Elimina y devuelve el elemento al final de la lista (el {@code ultimo}).
     * Operación de tiempo constante O(1) gracias a los enlaces dobles.
     *
     * @return El dato del elemento eliminado.
     * @throws NoSuchElementException si la lista está vacía.
     */
    public T eliminarAlFinal() {
        if (estaVacia()) {
            throw new NoSuchElementException("No se puede eliminar de una lista circular vacía.");
        }
        if (this.tamanno == 1) { // Caso especial
            return eliminarAlInicio();
        }

        T datoEliminado = this.ultimo.getDato();
        NodoDoble<T> penultimo = this.ultimo.getAnterior();
        NodoDoble<T> cabeza = this.ultimo.getSiguiente();
        
        // El penúltimo ahora apunta a la cabeza como siguiente
        penultimo.setSiguiente(cabeza);
        // La cabeza ahora apunta al penúltimo como anterior
        cabeza.setAnterior(penultimo);
        // El penúltimo se convierte en el nuevo 'ultimo'
        this.ultimo = penultimo;
        
        this.tamanno--;
        return datoEliminado;
    }

    /**
     * Elimina la primera ocurrencia del elemento especificado {@code dato} de la lista.
     * Utiliza {@code equals()} para la comparación. La búsqueda es O(n).
     *
     * @param dato El dato del elemento a eliminar.
     * @return {@code true} si el elemento fue encontrado y eliminado, {@code false} en caso contrario.
     */
    public boolean eliminar(T dato) {
        if (estaVacia()) {
            return false;
        }

        // Buscar el nodo a eliminar
        NodoDoble<T> nodoAEliminar = buscarNodo(dato);
        if (nodoAEliminar == null) {
            return false; // No se encontró
        }

        // Caso especial: solo un elemento
        if (this.tamanno == 1) {
            this.ultimo = null;
        } else {
            NodoDoble<T> nodoAnterior = nodoAEliminar.getAnterior();
            NodoDoble<T> nodoSiguiente = nodoAEliminar.getSiguiente();
            
            // Conectar el anterior con el siguiente
            nodoAnterior.setSiguiente(nodoSiguiente);
            nodoSiguiente.setAnterior(nodoAnterior);
            
            // Si el nodo eliminado era el último, actualizar la referencia
            if (nodoAEliminar == this.ultimo) {
                this.ultimo = nodoAnterior;
            }
        }
        
        this.tamanno--;
        return true;
    }

    /**
     * Elimina y devuelve el elemento que se encuentra inmediatamente después de la primera
     * ocurrencia del nodo que contiene {@code datoExistente}.
     * Si {@code datoExistente} no se encuentra, o si es el único nodo, no se elimina nada.
     * La búsqueda es O(n).
     *
     * @param datoExistente El dato del nodo referencia. Se compara usando {@code equals()}.
     * @return El dato del nodo eliminado, o {@code null} si no se pudo realizar la eliminación.
     */
    public T eliminarDespuesDe(T datoExistente) {
        NodoDoble<T> nodoExistente = buscarNodo(datoExistente);

        // No se puede eliminar si no se encuentra o si es el único nodo
        if (nodoExistente == null || this.tamanno <= 1) {
            return null;
        }

        NodoDoble<T> nodoAEliminar = nodoExistente.getSiguiente();
        T datoEliminado = nodoAEliminar.getDato();
        
        NodoDoble<T> nodoSiguienteDelEliminado = nodoAEliminar.getSiguiente();
        
        // Conectar nodoExistente con el siguiente del eliminado
        nodoExistente.setSiguiente(nodoSiguienteDelEliminado);
        nodoSiguienteDelEliminado.setAnterior(nodoExistente);

        // Si el nodo eliminado era el 'ultimo', nodoExistente pasa a ser el nuevo 'ultimo'
        if (nodoAEliminar == this.ultimo) {
            this.ultimo = nodoExistente;
        }
        this.tamanno--;
        return datoEliminado;
    }

    /**
     * Elimina y devuelve el elemento que se encuentra inmediatamente antes de la primera
     * ocurrencia del nodo que contiene {@code datoExistente}.
     * Requiere al menos dos nodos. 
     * La búsqueda es O(n).
     *
     * @param datoExistente El dato del nodo referencia. Se compara usando {@code equals()}.
     * @return El dato del nodo eliminado, o {@code null} si no se pudo realizar la eliminación.
     */
    public T eliminarAntesDe(T datoExistente) {
        if (this.tamanno <= 1) { // Imposible eliminar antes con 0 o 1 nodo
            return null;
        }

        NodoDoble<T> nodoExistente = buscarNodo(datoExistente);
        if (nodoExistente == null) {
            return null; // No se encontró el nodo referencia
        }

        NodoDoble<T> nodoAEliminar = nodoExistente.getAnterior();
        T datoEliminado = nodoAEliminar.getDato();
        
        NodoDoble<T> nodoAnteriorDelEliminado = nodoAEliminar.getAnterior();
        
        // Conectar el anterior del eliminado con el nodo existente
        nodoAnteriorDelEliminado.setSiguiente(nodoExistente);
        nodoExistente.setAnterior(nodoAnteriorDelEliminado);
        
        // Si el nodo eliminado era el 'ultimo', actualizar la referencia
        if (nodoAEliminar == this.ultimo) {
            this.ultimo = nodoAnteriorDelEliminado;
        }
        
        this.tamanno--;
        return datoEliminado;
    }

    // --- Otras Utilidades ---

    /**
     * Elimina todos los elementos de la lista, dejándola vacía.
     * Anula la referencia {@code ultimo} y establece el tamaño a 0.
     */
    public void borrarLista() {
        this.ultimo = null;
        this.tamanno = 0;
    }

    /**
     * Crea y devuelve una copia superficial (shallow copy) de esta lista circular.
     * Se crean nuevos nodos {@link NodoDoble}, pero contienen referencias a los mismos
     * objetos de datos que la lista original.
     *
     * @return Una nueva instancia de {@code ListaCircularDoble} con los mismos datos.
     */
    public ListaCircularDoble<T> clonarLista() {
        ListaCircularDoble<T> clon = new ListaCircularDoble<>();
        if (estaVacia()) {
            return clon;
        }
        NodoDoble<T> actual = getCabeza();
        for (int i = 0; i < this.tamanno; i++) {
            clon.insertarAlFinal(actual.getDato()); // Usar la inserción eficiente
            actual = actual.getSiguiente();
        }
        return clon;
    }


    /*
     * Devuelve el elemento que sigue al nodo que contiene {@code datoActual}.
     * Si {@code datoActual} no se encuentra, devuelve {@code null}.
     * La búsqueda es O(n).
     * @param datoActual El dato del nodo actual.
     * @return El dato del siguiente nodo, o {@code null} si no se encuentra.
     * @throws NoSuchElementException si la lista está vacía.
     */
    public T obtenerSiguiente(T datoActual) {
        NodoDoble<T> nodoActual = buscarNodo(datoActual);
        if (nodoActual == null) {
            return null; // No se encontró el nodo con el dato dado
        }
        return nodoActual.getSiguiente().getDato();
    }

    /*
     * Devuelve el elemento que precede al nodo que contiene {@code datoActual}.
     * Si {@code datoActual} no se encuentra, devuelve {@code null}.
     * La búsqueda es O(n).
     * @param datoActual El dato del nodo actual.
     * @return El dato del nodo anterior, o {@code null} si no se encuentra.
     * @throws NoSuchElementException si la lista está vacía.
     */
    public T obtenerAnterior(T datoActual) {
        NodoDoble<T> nodoActual = buscarNodo(datoActual);
        if (nodoActual == null) {
            return null; // No se encontró el nodo con el dato dado
        }
        return nodoActual.getAnterior().getDato();
    }


    /*
     * Devuelve el pastor más rico según el dinero.
     * Si la lista está vacía, devuelve null.
     * Recorre toda la lista una vez, comparando cada pastor con el más rico encontrado
     * hasta el momento.
     * @return El pastor más rico, o null si la lista está vacía.
     */
    public Pastor obtenerPrimerPastor() {
        if (estaVacia()) {
            return null;
        }
        return (Pastor) getCabeza().getDato();
    }


    /*
     * Devuelve el último pastor en la lista.
     * Si la lista está vacía, devuelve null.
     * @return El último pastor, o null si la lista está vacía.
     */
    public Pastor obtenerUltimoPastor() {
        if (estaVacia()) {
            return null;
        }
        return (Pastor) this.ultimo.getDato();
    }

    /*
     * Devuelve el pastor que tiene la posición especificada.
     * Si no se encuentra ningún pastor con esa posición, devuelve null.
     * La búsqueda es O(n).
     * @param posicion La posición del pastor a buscar.
     * @return El pastor con la posición dada, o null si no se encuentra.
     */
    public Pastor obtenerPastorPorPosicion(int posicion) {
        if (estaVacia()) {
            return null;
        }
        NodoDoble<T> actual = getCabeza();
        for (int i = 0; i < this.tamanno; i++) {
            Pastor pastor = (Pastor) actual.getDato();
            if (pastor.getPosicion() == posicion) {
                return pastor;
            }
            actual = actual.getSiguiente();
        }
        return null; // No se encontró ningún pastor con la posición dada
    }


    /**
     * Imprime una representación textual de la lista circular en la consola estándar.
     * Muestra los elementos desde la cabeza, indicando la conexión final a la cabeza.
     */
    public void imprimir() {
        if (estaVacia()) {
            System.out.println("Lista Circular Vacía");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("HEAD -> ");
        NodoDoble<T> actual = getCabeza();
        for (int i = 0; i < this.tamanno; i++) {
            sb.append(actual); // Usa NodoDoble.toString()
            actual = actual.getSiguiente();
            if (i < this.tamanno - 1) {
                sb.append(" <-> ");
            }
        }
        sb.append(" -> (HEAD)"); // Indica circularidad
        System.out.println(sb.toString());
    }

    // --- Métodos Auxiliares Privados ---

    /**
     * Busca el primer nodo en la lista que contiene el {@code datoBusqueda}.
     * Utiliza {@code Objects.equals()} para manejar {@code null}.
     * Recorre la lista circular una vez.
     *
     * @param datoBusqueda El dato a buscar.
     * @return El {@link NodoDoble} que contiene el dato, o {@code null} si no se encuentra.
     */
    public NodoDoble<T> buscarNodo(T datoBusqueda) {
        if (estaVacia()) {
            return null;
        }
        NodoDoble<T> actual = getCabeza();
        for (int i = 0; i < this.tamanno; i++) {
            if (Objects.equals(actual.getDato(), datoBusqueda)) {
                return actual; // Encontrado
            }
            actual = actual.getSiguiente(); // Avanza
        }
        return null; // No encontrado después de una vuelta completa
    }

    
    /*
     * Busca y devuelve el elemento "más rico" según el comparador proporcionado.
     * Si la lista está vacía, devuelve null.
     * Recorre toda la lista una vez, comparando cada elemento con el mejor encontrado
     * hasta el momento.
     * @param comparador El comparador que define el criterio de "más rico".
     * @return El elemento "más rico", o null si la lista está vacía.
     * @throws NullPointerException si el comparador es null.
     */
    public T obtenerMasRico(Comparator<T> comparador) {
        if (estaVacia()){
            return null;
        }
        NodoDoble<T> actual = getCabeza();
        T mejor = actual.getDato();

        for (int i = 0; i < this.tamanno; i++) {
            T candidato = actual.getDato();
            if (comparador.compare(candidato, mejor) > 0) {
                mejor = candidato;
            }
            actual = actual.getSiguiente();
        }
        return mejor;
    }


    /**
     * Busca el nodo que precede inmediatamente al primer nodo que contiene {@code datoBusqueda}.
     * En una lista doblemente enlazada circular, esto es más eficiente usando getAnterior().
     * Utiliza {@code Objects.equals()} para manejar {@code null}.
     *
     * @param datoBusqueda El dato contenido en el nodo objetivo (el nodo *después* del que buscamos).
     * @return El nodo predecesor, o {@code null} si {@code datoBusqueda} no se encuentra.
     */
    private NodoDoble<T> buscarNodoAnterior(T datoBusqueda) {
        NodoDoble<T> nodoEncontrado = buscarNodo(datoBusqueda);
        return (nodoEncontrado != null) ? nodoEncontrado.getAnterior() : null;
    }
}

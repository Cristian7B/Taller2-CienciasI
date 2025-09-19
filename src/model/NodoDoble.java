package model;

import java.util.Objects;

/**
 * Representa un nodo genérico para ser utilizado en listas doblemente enlazadas
 * ({@link ListaEnlazadaDoble}).
 * Almacena un dato de tipo {@code T} y referencias tanto al nodo siguiente como al anterior.
 *
 * @param <T> El tipo del dato almacenado en el nodo.
 * @author devapps
 * @version 1.1
 */
public class NodoDoble<T> {

    /** El dato o elemento almacenado en este nodo. */
    private T dato;
    /** Referencia al siguiente nodo en la lista, o {@code null} si es la cola. */
    private NodoDoble<T> siguiente;
    /** Referencia al nodo anterior en la lista, o {@code null} si es la cabeza. */
    private NodoDoble<T> anterior;

    /**
     * Construye un nodo doblemente enlazado con el dato especificado.
     * Las referencias {@code anterior} y {@code siguiente} se inicializan a {@code null}.
     *
     * @param dato El dato a almacenar.
     */
    public NodoDoble(T dato) {
        this(dato, null, null); // Delega al constructor completo
    }

    /**
     * Construye un nodo doblemente enlazado con el dato y las referencias
     * anterior y siguiente especificadas.
     *
     * @param dato El dato a almacenar.
     * @param anterior El nodo que precede a este nodo.
     * @param siguiente El nodo que sucede a este nodo.
     */
    public NodoDoble(T dato, NodoDoble<T> anterior, NodoDoble<T> siguiente) {
        this.dato = dato;
        this.anterior = anterior;
        this.siguiente = siguiente;
    }

    // --- Getters ---

    /**
     * Obtiene el dato almacenado en este nodo.
     * @return El dato de tipo {@code T}.
     */
    public T getDato() {
        return dato;
    }

    /**
     * Obtiene la referencia al siguiente nodo en la lista.
     * @return El {@link NodoDoble} siguiente, o {@code null} si es el último.
     */
    public NodoDoble<T> getSiguiente() {
        return siguiente;
    }

    /**
     * Obtiene la referencia al nodo anterior en la lista.
     * @return El {@link NodoDoble} anterior, o {@code null} si es el primero.
     */
    public NodoDoble<T> getAnterior() {
        return anterior;
    }

    // --- Setters ---

    /**
     * Establece o actualiza el dato almacenado en este nodo.
     * @param dato El nuevo dato a almacenar.
     */
    public void setDato(T dato) {
        this.dato = dato;
    }

    /**
     * Establece o actualiza la referencia al siguiente nodo.
     * @param siguiente El que será el nuevo nodo siguiente.
     */
    public void setSiguiente(NodoDoble<T> siguiente) {
        this.siguiente = siguiente;
    }

    /**
     * Establece o actualiza la referencia al nodo anterior.
     * @param anterior El que será el nuevo nodo anterior.
     */
    public void setAnterior(NodoDoble<T> anterior) {
        this.anterior = anterior;
    }

    /**
     * Devuelve una representación textual del dato contenido en el nodo.
     * Si el dato es {@code null}, devuelve la cadena "null".
     *
     * @return La representación en cadena del dato.
     */
    @Override
    public String toString() {
        return Objects.toString(dato, "null");
    }

    // --- equals y hashCode ---
    // Aplica la misma nota que en Nodo: generalmente no se sobrescriben para la clase Nodo en sí.
}

package com.felink.service.dispose.dynamic.delaunay;

/*
 * Copyright (c) 2007 by L. Paul Chew.
 *
 * Permission is hereby granted, without written agreement and without
 * license or royalty fees, to use, copy, modify, and distribute this
 * software and its documentation for any purpose, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

import com.felink.service.common.model.BasePoint;
import com.felink.service.common.model.BaseTriangle;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * A Triangle is an immutable Set of exactly three Pnts.
 * <p/>
 * All Set operations are available. Individual vertices can be accessed via
 * iterator() and also via triangle.get(index).
 * <p/>
 * Note that, even if two triangles have the same vertex set, they are
 * *different* triangles. Methods equals() and hashCode() are consistent with
 * this rule.
 *
 * @author Paul Chew
 *         <p/>
 *         Created December 2007. Replaced general simplices with geometric triangle.
 */
public class Triangle extends ArraySet<Pnt> {

    private int idNumber;                   // The id number
    private Pnt circumcenter = null;        // The triangle's circumcenter

    private static int idGenerator = 0;     // Used to create id numbers
    public static boolean moreInfo = false; // True iff more info in toString

    /**
     * @param vertices the vertices of the Triangle.
     * @throws IllegalArgumentException if there are not three distinct vertices
     */
    public Triangle(Pnt... vertices) {
        this(Arrays.asList(vertices));
    }

    /**
     * @param collection a Collection holding the Simplex vertices
     * @throws IllegalArgumentException if there are not three distinct vertices
     */
    public Triangle(Collection<? extends Pnt> collection) {
        super(collection);
        idNumber = idGenerator++;
        if (this.size() != 3)
            throw new IllegalArgumentException("Triangle must have 3 vertices");
    }

    /**
     * Get arbitrary vertex of this triangle, but not any of the bad vertices.
     *
     * @param badVertices one or more bad vertices
     * @return a vertex of this triangle, but not one of the bad vertices
     * @throws NoSuchElementException if no vertex found
     */
    public Pnt getVertexButNot(Pnt... badVertices) {
        Collection<Pnt> bad = Arrays.asList(badVertices);
        for (Pnt v : this) if (!bad.contains(v)) return v;
        throw new NoSuchElementException("No vertex found");
    }

    public Pnt getVertexButNot(Triangle triangle) {
        return getVertexButNot(triangle.toArray());
    }

    /**
     * True iff triangles are neighbors. Two triangles are neighbors if they
     * share a facet.
     *
     * @param triangle the other Triangle
     * @return true iff this Triangle is a neighbor of triangle
     */
    public boolean isNeighbor(Triangle triangle) {
        int count = 0;
        for (Pnt vertex : this)
            if (!triangle.contains(vertex)) count++;
        return count == 1;
    }

    /**
     * Report the facet opposite vertex.
     *
     * @param vertex a vertex of this Triangle
     * @return the facet opposite vertex
     * @throws IllegalArgumentException if the vertex is not in triangle
     */
    public ArraySet<Pnt> facetOpposite(Pnt vertex) {
        ArraySet<Pnt> facet = new ArraySet<Pnt>(this);
        if (!facet.remove(vertex))
            throw new IllegalArgumentException("Vertex not in triangle");
        return facet;
    }

    /**
     * @return the triangle's circumcenter
     */
    public Pnt getCircumcenter() {
        if (circumcenter == null)
            circumcenter = Pnt.circumcenter(this.toArray(new Pnt[0]));
        return circumcenter;
    }

    public BaseTriangle toBaseTriangle() {
        BaseTriangle triangle = new BaseTriangle();
        for(Pnt pnt: this) {
            triangle.add(new BasePoint(pnt.coord(0), pnt.coord(1)));
        }
        return triangle;
    }

    /* The following two methods ensure that a Triangle is immutable */
    @Override
    public Pnt[] toArray() {
        // Estimate size of array; be prepared to see more or fewer elements
        Pnt[] r = new Pnt[size()];
        Iterator<Pnt> it = iterator();
        for (int i = 0; i < r.length; i++) {
            if (! it.hasNext()) // fewer elements than expected
                return Arrays.copyOf(r, i);
            r[i] = it.next();
        }
        return r;
    }

    @Override
    public String toString() {
//        if (!moreInfo) return "Triangle" + idNumber;
//        return "Triangle" + idNumber + super.toString();
        return get(0) +  " " + get(1) + " " + get(2);
    }

    @Override
    public boolean add(Pnt vertex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<Pnt> iterator() {
        return new Iterator<Pnt>() {
            private Iterator<Pnt> it = Triangle.super.iterator();

            public boolean hasNext() {
                return it.hasNext();
            }

            public Pnt next() {
                return it.next();
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    /* The following two methods ensure that all triangles are different. */

    @Override
    public int hashCode() {
        int hashCode = 1;
        for(Pnt p :this){
            hashCode += p.hashCode();
        }
        return hashCode;
//        return (int) (idNumber ^ (idNumber >>> 32));
    }

    @Override
    public boolean equals(Object o) {
        return (this == o);
    }

}

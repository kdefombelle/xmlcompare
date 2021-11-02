package fr.kdefombelle.xmlcompare.core;

import java.io.File;
import java.io.IOException;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;


public final class FileResourcesRule implements MethodRule {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Instance fields
    //~ ----------------------------------------------------------------------------------------------------------------

    private final Map<String, FileResource> files = new LinkedHashMap<String, FileResource>();

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods
    //~ ----------------------------------------------------------------------------------------------------------------

    /**
     * <p>Returns the content as {@code String} of the file resource by path as declared in the {@code files} attribute
     * of the {@link FileResources} annotation.</p>
     *
     * @param  path path of the file resource to return the content from
     *
     * @return the content as {@code String} of the file resource
     *
     * @throws IllegalArgumentException if no file resource exits for the path
     */
    public String read(String path) {
        if (!files.containsKey(path))
            throw new IllegalArgumentException("The path [" + path +
                    "] does not reference any file");

        return files.get(path).string;
    }

    /**
     * <p>Returns the content as {@code String} of the file resource by index as declared in the {@code files} attribute
     * of the {@link FileResources} annotation.</p>
     *
     * @param  index index of the file resource to return the name
     *
     * @return the content as {@code String} of the file resource
     *
     * @throws IndexOutOfBoundsException if no file resource exits for the index
     */
    public String read(int index) {
        int i = 0;
        for (FileResource file : files.values())
            if (index == i++)
                return file.string;

        throw new IndexOutOfBoundsException("The index [" + index +
                "] is out of range [0, " + files.size() + "]");
    }

    /**
     * <p>Returns the name of the file resource by index as declared in the {@code files} attribute of the {@link
     * FileResources} annotation.</p>
     *
     * @param  index index of the file resource to return the name
     *
     * @return the name of the file resource
     *
     * @throws IndexOutOfBoundsException if no file resource exits for the index
     */
    public String name(int index) {
        int i = 0;
        for (FileResource file : files.values())
            if (index == i++)
                return file.name;

        throw new IndexOutOfBoundsException("The index [" + index +
                "] is out of range [0, " + files.size() + "]");
    }

    @Override
    public Statement apply(final Statement base, final FrameworkMethod method, final Object target) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable { // NOSONAR
                before(method);
                try {
                    base.evaluate();
                } finally {
                    after();
                }
            }
        };
    }

    private void before(FrameworkMethod method) throws IOException {
        FileResources resources = method.getAnnotation(FileResources.class);
        if (resources != null)
            for (String path : resources.files())
                files.put(path, new FileResource(new File(path).getName(), StringUtil.fromInputStream(StringUtil.loadResource(method.getClass(), path), resources.charset())));
    }

    private void after() {
        files.clear();
    }

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Inner Classes
    //~ ----------------------------------------------------------------------------------------------------------------

    private static final class FileResource {

        private final String name;

        private final String string;

        private FileResource(String name, String string) {
            this.name = name;
            this.string = string;
        }
    }
}
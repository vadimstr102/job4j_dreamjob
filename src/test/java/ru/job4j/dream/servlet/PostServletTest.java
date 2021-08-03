package ru.job4j.dream.servlet;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.store.MemStore;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PsqlStore.class)
public class PostServletTest {
    @Test
    public void whenCreatePost() throws IOException {
        MemStore store = MemStore.instOf();

        PowerMockito.mockStatic(PsqlStore.class);
        PowerMockito.when(PsqlStore.instOf()).thenReturn(store);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        PowerMockito.when(req.getParameter("id")).thenReturn("0");
        PowerMockito.when(req.getParameter("name")).thenReturn("n");

        new PostServlet().doPost(req, resp);

        Post result = store.findAllPosts().iterator().next();
        Assert.assertThat(result.getName(), is("n"));
    }

    @Test
    public void whenDoGet() throws IOException, ServletException {
        String path = "posts.jsp";
        String postsAttributeKey = "posts";
        String userAttributeKey = "user";
        Object userAttributeValue = new Object();
        MemStore store = MemStore.instOf();
        store.savePost(new Post(0, "n"));

        PowerMockito.mockStatic(PsqlStore.class);
        PowerMockito.when(PsqlStore.instOf()).thenReturn(store);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        PowerMockito.when(req.getSession()).thenReturn(session);
        PowerMockito.when(session.getAttribute(userAttributeKey)).thenReturn(userAttributeValue);
        PowerMockito.when(req.getRequestDispatcher(path)).thenReturn(dispatcher);

        new PostServlet().doGet(req, resp);

        verify(req, times(1)).setAttribute(postsAttributeKey, store.findAllPosts());
        verify(req, times(1)).setAttribute(userAttributeKey, userAttributeValue);
        verify(req, times(1)).getRequestDispatcher(path);
        verify(dispatcher, times(1)).forward(req, resp);
    }
}

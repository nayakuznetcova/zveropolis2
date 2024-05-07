package ru.skypro.zveropolis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.zveropolis.relocation.Relocation;
import ru.skypro.zveropolis.relocation.State;
import ru.skypro.zveropolis.repository.SubscriberRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RelocationTest {

    @Mock
    Relocation newRelocation;

    @Test
    void createNewRelocation() {

        Relocation newRelocation = mock(Relocation.class);
//        Relocation myRelocation = new Relocation();

        when(newRelocation.getState(any(Long.class))).thenReturn((State) newRelocation);
//        assertEquals(0, newRelocation.getState(1l));
    }

    private RelocationTest relocationTest;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Mock
    Relocation getNewRelocation;
    @Mock
    private SubscriberRepository subscriberRepository;

//    @Test
//    void getOwner(){
//        Relocation newRelocation = Mockito.mock(Relocation.class);
//        when(newRelocation.getOwner()).thenReturn("123");
//        assertEquals("123", newRelocation.getOwner());
//    }
//
//    @Test
//    void verificationTest() {
//        Relocation newRelocation = Mockito.mock(Relocation.class);
//        verify(newRelocation);
//        assertEquals(null, newRelocation.getOwner());
//
//    }
}